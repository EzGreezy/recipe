import readchar

for i in range(10):
    print("Reading a char:")
    c = readchar.readkey()
    if c == ' ':
        print("was space")
    if c == ';':
        print("was ;")
