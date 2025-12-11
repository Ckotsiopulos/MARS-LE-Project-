# Pokemon Assembly Test
# IMPORTANT: Select "Pokemon Assembly" from Tools -> Language Switcher!

# Initialize stats
li $1, 100    # Player HP
li $2, 80     # Enemy HP
li $3, 25     # Player Attack
li $4, 20     # Enemy Attack

# Battle turn 1
turn          # Increment turn counter
crit $2, $3   # Check for critical hit
dmg $2, $3    # Player attacks enemy

# Battle turn 2
turn          # Increment turn counter
crit $1, $4   # Check for critical hit
dmg $1, $4    # Enemy attacks player

# Apply status effects
poison $2     # Poison enemy
burn $1       # Burn player

# Heal player
heal $1, 15   # Heal player by 15 HP

# Modify stats
stat_up $3, 5   # Increase player attack by 5
stat_down $4, 3 # Decrease enemy attack by 3

# Test Pokemon encounter (simple version)
li $5, 2      # Use Ultra Ball (0=Poke, 1=Great, 2=Ultra, 3=Master)
encounter $5, $6, $7  # $6 = Pokemon ID, $7 = caught (1 or 0)

# Test Pokemon encounter with user input
init_inventory        # Initialize pokeball inventory
encounter_input $8, $9  # Interactive encounter - will prompt for input

# End
li $0, 0