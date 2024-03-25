import sys

def main():
    if len(sys.argv) != 2:
        print("Usage: analyze_pmd.py <pmd_html_file>")
        sys.exit(1)

    pmd_html_file = sys.argv[1]
    with open(pmd_html_file, 'r') as f:
        if 'com/AlertSystem' in f.read():
            print("String 'com/AlertSystem' found in PMD report.")
            sys.exit(1)

if __name__ == "__main__":
    main()

