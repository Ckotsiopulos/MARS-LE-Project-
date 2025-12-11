.text
main:

    # make 2 pokemon
    li $t0, 120      # p1 hp
    li $t1, 55       # p1 atk
    li $t2, 150      # p2 hp
    li $t3, 30       # p2 atk

    # stat tweaks
    stat_up $t1, 10  # p1 buff
    stat_down $t3, 5 # p2 nerf

    # p1 hits
    turn
    crit $t2,$t1     # check crit
    dmg  $t2,$t1     # p2 takes dmg

    # status stuff
    poison $t2
    burn   $t2

    # p2 hits
    turn
    crit $t0,$t3     # probably no crit
    dmg  $t0,$t3     # p1 takes dmg

    # quick heal
    heal $t2,20

    # encounters
    init_inventory
    li $t4,2         # pokeball type (whatever your impl expects)
    encounter $t4,$t5    # auto encounter, uses 2 regs like the error says

    # interactive encounter (current jar wants 1 reg)
    encounter_input $t6   # t6 will hold whatever your impl writes

end:
    # fake “exit” – just loop forever
    beq $t0,$t0,end