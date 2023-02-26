    mov EAX 6
f1: mov EBX 1
f2: mov ZZZ 1
f3: mul EBX EAX
    sub EAX ECX
    jnz EAX f3
    out EBX