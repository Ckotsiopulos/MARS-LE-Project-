.text
main:
    li $t0, 10
    li $t1, 5
    add $t2, $t0, $t1
    sub $t3, $t0, $t1
    mul $t4, $t0, $t1
    div $t5, $t4, $t1
    
    li $t6, 12
    li $t7, 10
    and $s0, $t6, $t7
    or  $s1, $t6, $t7
    xor $s2, $t6, $t7
    
    mov $s3, $s2
    
    beq $s3, $s2, branch_test
    li $s4, 999
branch_test:
    li $s4, 123
    
    beq $s4, $zero, skip_this
    li $s5, 456
skip_this:
    
    li $t0, 100
    li $t1, 15
    li $t2, 75
    
    crit $t0, $t2
    dmg $t0, $t1
    
    poison $t0
    burn $t0
    
    turn
    turn
    
    heal $t0, 25
    
    stat_up $t1, 5
    stat_down $t1, 3
    
    li $t0, 0
    encounter $t0, $t1
    
    li $t0, 1
    encounter $t0, $t2
    
    li $t0, 2
    encounter $t0, $t3
    
    li $t0, 3
    encounter $t0, $t4
    
    init_inventory
    
    encounter_input $s6
    
    beq $zero, $zero, end
end:
