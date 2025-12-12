.text
main:
    # Arithmetic and bitwise
    li $t0, 5
    li $t1, 3
    add $t2, $t0, $t1      # 8
    sub $t3, $t2, $t1      # 5
    mul $t4, $t0, $t1      # 15
    div $t5, $t4, $t1      # 5
    and $t6, $t4, $t1      # 3
    or  $t7, $t4, $t1      # 15
    xor $s0, $t4, $t1      # 12
    mov $s1, $s0           # 12

    # Branch checks
    beq $s1, $s0, branch_hit
    li $s2, 999            # skipped
branch_hit:
    beq $s1, $zero, skip_branch
    li $s2, 123            # taken
skip_branch:

    # Battle mechanics
    li $t0, 50             # HP
    li $t1, 12             # attack
    li $t2, 75             # crit chance > 50 -> sets crit flag
    crit $t0, $t2
    dmg $t0, $t1           # doubles attack because of crit
    poison $t0
    burn  $t0
    turn                   # increments R11
    heal $t0, 20
    stat_up   $t3, 5
    stat_down $t3, 2

    # Encounter without user input (uses $t0 as ball type, writes caught to $t1)
    li $t0, 2              # Ultra Ball
    encounter $t0, $t1

    # Inventory + encounter with user input
    init_inventory
    encounter_input $t2    # writes caught flag to $t2, prompts for ball/run

    # End
    beq $zero, $zero, end
end: