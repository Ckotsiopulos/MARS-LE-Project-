# Pokemon Assembly Test Program
# This program demonstrates the Pokemon battle system

# Setup: Initialize Pokemon stats
# Pokemon 1 (Pikachu) - ID 1
li $t0, 1          # Pokemon 1 ID
li $t1, 100         # HP
li $t2, 25          # Attack
li $t3, 15          # Defense

# Pokemon 2 (Charmander) - ID 2  
li $t4, 2           # Pokemon 2 ID
li $t5, 80          # HP
li $t6, 30          # Attack
li $t7, 10          # Defense

# Show the battle preview
show_battle $t0, $t4

# Initialize HP registers
mov $1, $t1         # Set Pokemon 1 HP to register 1
mov $5, $t5         # Set Pokemon 2 HP to register 5 (using $t5 as temp)

# Turn 1: Pokemon 1 attacks Pokemon 2
turn                # Increment turn counter
dmg $5, $t2         # Pokemon 2 takes damage from Pokemon 1's attack
                    # Result: $5 = 80 - 25 = 55

# Turn 2: Pokemon 2 attacks Pokemon 1
turn                # Increment turn counter
dmg $1, $t6         # Pokemon 1 takes damage from Pokemon 2's attack
                    # Result: $1 = 100 - 30 = 70

# Turn 3: Check for critical hit chance
turn                # Increment turn counter
li $t8, 75          # Critical chance (75 > 50, so crit will trigger)
crit $1, $t8        # Check crit - sets register 8 to 1
dmg $5, $t2         # Pokemon 2 takes CRITICAL damage (doubled)
                    # Result: $5 = 55 - (25 * 2) = 5

# Turn 4: Apply status effects
turn                # Increment turn counter
poison $5           # Apply poison to Pokemon 2 (sets bit 0 in register 7)
burn $1             # Apply burn to Pokemon 1 (sets bit 1 in register 7)

# Turn 5: Use healing
turn                # Increment turn counter
heal $1, 20         # Heal Pokemon 1 by 20 HP
                    # Result: $1 = 70 + 20 = 90

# Turn 6: Stat modifications
turn                # Increment turn counter
stat_up $t2, 5      # Increase Pokemon 1's attack by 5
                    # Result: $t2 = 25 + 5 = 30
stat_down $t6, 3    # Decrease Pokemon 2's attack by 3
                    # Result: $t6 = 30 - 3 = 27

# Final attack with modified stats
dmg $5, $t2         # Pokemon 2 takes damage with boosted attack
                    # Result: $5 = 5 - 30 = -5 (clamped to 0)

# End of battle
# Check final HP values:
# Register 1 ($1) = Pokemon 1 HP (should be 90)
# Register 5 ($5) = Pokemon 2 HP (should be 0)
# Register 11 ($11) = Turn counter (should be 6)
# Register 7 ($7) = Status flags (should have poison and burn bits set)

