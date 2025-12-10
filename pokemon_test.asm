# Pokemon Assembly - Complete Instruction Test
# Tests ALL 18 instructions in Pokemon Assembly
# Make sure "Pokemon Assembly" is selected in Tools -> Language Switcher

# ============================================
# SECTION 1: Basic Math Operations
# ============================================
li $1, 20         # Load immediate: $1 = 20
li $2, 15         # Load immediate: $2 = 15
li $3, 5          # Load immediate: $3 = 5

add $4, $1, $2    # Add: $4 = $1 + $2 = 20 + 15 = 35
sub $5, $1, $2    # Sub: $5 = $1 - $2 = 20 - 15 = 5
mul $6, $2, $3    # Mul: $6 = $2 * $3 = 15 * 5 = 75
div $23, $6, $3   # Div: $23 = $6 / $3 = 75 / 5 = 15

# ============================================
# SECTION 2: Bitwise Operations
# ============================================
li $8, 10         # $8 = 10 (binary 1010)
li $9, 12         # $9 = 12 (binary 1100)

and $10, $8, $9   # AND: $10 = $8 & $9 = 1010 & 1100 = 1000 = 8
or $11, $8, $9    # OR:  $11 = $8 | $9 = 1010 | 1100 = 1110 = 14
xor $12, $8, $9   # XOR: $12 = $8 ^ $9 = 1010 ^ 1100 = 0110 = 6

# ============================================
# SECTION 3: Move Operation
# ============================================
mov $13, $4       # Move: $13 = $4 = 35

# ============================================
# SECTION 4: Branch Operation
# ============================================
li $14, 10
li $15, 10
beq $14, $15, branch_target  # Branch if equal (should branch)
li $16, 999       # This should be skipped
branch_target:
li $16, 100        # This will execute after branch

# ============================================
# SECTION 5: Pokemon Battle Instructions
# ============================================
# Initialize Pokemon
li $17, 100       # Pokemon 1 HP
li $18, 30        # Pokemon 1 Attack
li $19, 80        # Pokemon 2 HP
li $20, 25        # Pokemon 2 Attack
li $7, 0          # Status register
li $21, 0         # Turn counter (using $21 instead of $11 for testing)

# Turn counter
turn              # Increment R11 (system register)

# Critical hit check
li $22, 75        # High crit chance
crit $19, $22     # Check crit for Pokemon 2 (sets R8 if > 50)

# Damage with crit
dmg $19, $18      # Pokemon 2 takes damage (30 * 2 = 60 due to crit)
                  # Pokemon 2 HP: 80 - 60 = 20

# Status effects
poison $17        # Poison Pokemon 1 (sets bit 0 in R7)
burn $19         # Burn Pokemon 2 (sets bit 1 in R7)
                  # Status (R7) = 0x1 | 0x2 = 3

# Healing
heal $19, 15      # Heal Pokemon 2 by 15
                  # Pokemon 2 HP: 20 + 15 = 35

# Stat modifications
stat_up $18, 10   # Boost Pokemon 1 attack by 10
                  # Pokemon 1 Attack: 30 + 10 = 40

stat_down $20, 5  # Lower Pokemon 2 attack by 5
                  # Pokemon 2 Attack: 25 - 5 = 20

# ============================================
# Expected Final Values:
# ============================================
# Math operations:
# $1 = 20, $2 = 15, $3 = 5
# $4 = 35  (add)
# $5 = 5   (sub)
# $6 = 75  (mul)
# $23 = 15 (div)
# $8 = 10, $9 = 12
# $10 = 8  (and)
# $11 = 14 (or)
# $12 = 6  (xor)
# $13 = 35 (mov)
# $14 = 10, $15 = 10
# $16 = 100 (beq branch taken)
# Pokemon:
# $17 = 100 (Pokemon 1 HP)
# $18 = 40  (Pokemon 1 Attack - boosted)
# $19 = 35  (Pokemon 2 HP - damaged and healed)
# $20 = 20  (Pokemon 2 Attack - lowered)
# $7 = 3    (Status: poison | burn)
# $11 = 1   (Turn counter - R11, but $11 was used for OR test)
# System register R11 = 1 (turn was called once)
