# Pokemon Assembly Battle Test
# This program demonstrates a complete Pokemon battle simulation

# ============================================
# Setup: Initialize Pokemon Stats
# ============================================

# Pokemon 1: Pikachu (ID 1)
li $t0, 1           # Pokemon 1 ID
li $t1, 100         # HP
li $t2, 25          # Attack
li $t3, 15          # Defense

# Pokemon 2: Charmander (ID 2)
li $t4, 2           # Pokemon 2 ID
li $t5, 80          # HP
li $t6, 30          # Attack
li $t7, 10          # Defense

# ============================================
# Show Battle Preview
# ============================================
show_battle $t0, $t4

# ============================================
# Initialize HP Registers
# ============================================
mov $1, $t1         # Set Pokemon 1 HP to register 1 ($1)
mov $5, $t5         # Set Pokemon 2 HP to register 5 ($5)

# ============================================
# Turn 1: Pokemon 1 Attacks
# ============================================
turn                # Increment turn counter
dmg $5, $t2         # Pokemon 2 takes 25 damage
                    # Pokemon 2 HP: 80 - 25 = 55

# ============================================
# Turn 2: Pokemon 2 Attacks
# ============================================
turn                # Increment turn counter
dmg $1, $t6         # Pokemon 1 takes 30 damage
                    # Pokemon 1 HP: 100 - 30 = 70

# ============================================
# Turn 3: Critical Hit!
# ============================================
turn                # Increment turn counter
li $t8, 75          # Critical chance (75 > 50, triggers crit)
crit $1, $t8        # Set crit flag
dmg $5, $t2         # Pokemon 2 takes CRITICAL damage (doubled!)
                    # Pokemon 2 HP: 55 - (25 * 2) = 5

# ============================================
# Turn 4: Status Effects
# ============================================
turn                # Increment turn counter
poison $5           # Apply poison to Pokemon 2
burn $1             # Apply burn to Pokemon 1

# ============================================
# Turn 5: Healing
# ============================================
turn                # Increment turn counter
heal $1, 20         # Heal Pokemon 1 by 20 HP
                    # Pokemon 1 HP: 70 + 20 = 90

# ============================================
# Turn 6: Stat Modifications
# ============================================
turn                # Increment turn counter
stat_up $t2, 5      # Increase Pokemon 1's attack by 5
                    # Attack: 25 + 5 = 30
stat_down $t6, 3    # Decrease Pokemon 2's attack by 3
                    # Attack: 30 - 3 = 27

# ============================================
# Turn 7: Final Attack with Boosted Stats
# ============================================
turn                # Increment turn counter
dmg $5, $t2         # Pokemon 2 takes damage with boosted attack
                    # Pokemon 2 HP: 5 - 30 = -5 (clamped to 0)

# ============================================
# Battle Complete!
# ============================================
# Final Results:
# - Register $1 = Pokemon 1 HP (should be 90)
# - Register $5 = Pokemon 2 HP (should be 0 - fainted!)
# - Register $11 = Turn counter (should be 7)
# - Register $7 = Status flags (poison and burn bits set)
# - Register $t2 = Pokemon 1 Attack (should be 30, boosted from 25)
# - Register $t6 = Pokemon 2 Attack (should be 27, reduced from 30)

