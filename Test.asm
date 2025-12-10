# Simple Pokemon Assembly Test
# Quick test to verify the language works

# Load Pokemon IDs
li $t0, 1           # Pikachu
li $t1, 2           # Charmander

# Show battle preview (this will print to console)
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
dmg $1, $t7         # HP = 80 - (20 * 2) = 40

# Test healing
heal $1, 10         # HP = 40 + 10 = 50

# Test turn counter
turn                # Increment turn counter (register 11)

# End program
