# Pokemon Battle Example - Simple Test
# 
# TO TEST IN MARS:
# 1. Open MARS.jar
# 2. Tools -> Language Switcher -> Select "Pokemon Assembly"
# 3. Open this file
# 4. Assemble (F3) and Run (F5)
# 5. Check Console for battle art output
# 6. Check Registers window to see HP (R1), Status (R7), Turns (R11)

# ===== SETUP: Show Battle =====
li $t0, 1           # Pokemon 1 ID (Pikachu)
li $t1, 2           # Pokemon 2 ID (Charmander)
show_battle $t0, $t1    # Display ASCII battle art in console

# ===== Initialize HP =====
# R1 is the HP register - initialize to 100
li $t2, 100
hp_up $t2, 100      # Add 100 to R1 (if R1 starts at 0, this sets it to 100)

# ===== TURN 1: Normal Attack =====
turn                # Increment turn counter (R11)
li $t3, 20          # Attack power = 20
li $t4, 0
mov $t4, $1         # Load current HP from R1 into $t4
dmg $t4, $t3        # Apply damage: $t4 = $t4 - 20
mov $1, $t4         # Save new HP back to R1

# ===== TURN 2: Critical Hit =====
turn
li $t5, 75          # Critical chance = 75 (> 50, so crit!)
li $t6, 0
crit $t6, $t5       # Set crit flag in R8 (since 75 > 50)
li $t7, 15          # Attack power = 15
li $t8, 0
mov $t8, $1         # Load current HP
dmg $t8, $t7        # Critical hit! Damage doubled (15*2=30)
mov $1, $t8         # Save HP

# ===== TURN 3: Healing =====
turn
heal $t0, 10        # Heal 10 HP (adds 10 to R1)

# ===== TURN 4: Status Effects =====
turn
poison $t0          # Apply poison status (sets bit 0 in R7)
burn $t1           # Apply burn status (sets bit 1 in R7)

# ===== TURN 5: Final Attack =====
turn
li $s0, 25          # Strong attack
li $s1, 0
mov $s1, $1         # Load HP
dmg $s1, $s0        # Apply 25 damage
mov $1, $s1         # Save HP

# ===== RESULTS =====
# After running, check these registers:
#   R1 ($1)  = Final HP (should be around 35-45 depending on initial state)
#   R7 ($7)  = Status flags (0x1=poison, 0x2=burn, 0x3=both)
#   R8 ($8)  = Crit flag (should be 0 after crit was used)
#   R11 ($11) = Turn counter (should be 5)
