import datetime
import json
import time

import aiogram
import asyncio
import requests
from aiokafka import AIOKafkaConsumer

from aiogram import Bot, Dispatcher, types

API_TOKEN = '5934732022:AAF61jlCQG9FAKmvXGTgyGaS3muS0szGRME'

bot = Bot(token=API_TOKEN)
dp = Dispatcher(bot)
dp.once = False


async def on_kafka_message():
    # Настройки Kafka консюмера
    consumer = AIOKafkaConsumer(
        'telegram-alert',
        bootstrap_servers='kafka:9092',
        value_deserializer=lambda x: x.decode('utf-8') if x else None,
        auto_offset_reset='latest'
    )

    try:
        await consumer.start()
        print(f"INFO {datetime.datetime.now()} Kafka consumer запущен.")

        while True:
            message = await consumer.getone()

            if message.value is not None:
                message_json = json.loads(message.value)

                await bot.send_message(message_json["chatId"],
                                       message_json["message"])
            await asyncio.sleep(5)
    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        await consumer.stop()


@dp.message_handler(commands=["start"])
async def send_welcome(message: types.Message):
    await message.reply(
        "Привет! Я эхо-бот для уведомления о проблеммах в вашем проекте. "
        "Давай авторизуем ваш проект, напиши мне id проекта, свой логин и пароль. "
        "Не преживай после авторизации я сразу удалю сообщение. Начните сообщение с команды /auth.")
    # await on_kafka_message()


@dp.message_handler(commands=["auth"])
async def echo(message: types.Message):
    data = message.text.split()

    url = 'http://backend-alert-api:8080/bot/authProject'  # Замените на ваш URL
    data = {
        "idProject": data[1],  # Замените на ваше значение
        "chatId": str(message.chat.id),  # Замените на ваше значение
        "login": data[2],  # Замените на ваше значение
        "password": data[3]  # Замените на ваше значение
    }
    headers = {'Content-Type': 'application/json'}

    response = requests.post(url, data=json.dumps(data), headers=headers)

    if (response.json()["status"] == "success"):
        await message.answer("Проект успешно привязан к вашему чату")
    else:
        await message.answer(
            "Ошибка, проверьте наличие проекта и ваш доступ к нему, корректность логина и пароля")

    await bot.delete_message(message.chat.id, message.message_id)
    await on_kafka_message()


async def main():
    print(f"INFO {datetime.datetime.now()} бот запущен.")
    await dp.start_polling(bot)


asyncio.run(main())
