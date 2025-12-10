# Quick Pokemon Assembly Test
# Simple test to verify everything works

# Load Pokemon IDs
li $t0, 1           # Pikachu
li $t1, 2           # Charmander

# Show battle preview (check Run I/O tab!)
show_battle $t0, $t1

# Test basic arithmetic
li $t2, 10
li $t3, 5
add $t4, $t2, $t3   # $t4 = 15
sub $t5, $t2, $t3   # $t5 = 5
mul $t6, $t2, $t3   # $t6 = 50

# Test Pokemon battle mechanics
li $1, 100          # Set HP in register 1
li $t7, 20          # Attack power
dmg $1, $t7         # HP = 100 - 20 = 80

# Test critical hit
li $t8, 60          # Critical chance (60 > 50)
crit $1, $t8        # Sets crit flag
dmg $1, $t7         # HP = 80 - (20 * 2) = 40 (critical!)

# Test healing
heal $1, 10         # HP = 40 + 10 = 50

# Test turn counter
turn                # Increment turn counter (register 11)

# Check registers after running:
# $1 = 50 (HP after damage and healing)
# $11 = 1 (turn counter)
# $t4 = 15 (addition result)
# $t5 = 5 (subtraction result)
# $t6 = 50 (multiplication result)

