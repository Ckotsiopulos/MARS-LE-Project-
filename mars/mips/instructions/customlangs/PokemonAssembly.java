package mars.mips.instructions.customlangs;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.mips.instructions.*;
    import mars.util.SystemIO;

public class PokemonAssembly extends CustomAssembly {
    @Override
    public String getName(){
        return "Pokemon Assembly";
    }

    @Override
    public String getDescription(){
        return "Assembly language to let your computer battle pokemon";
    }

    @Override
    protected void populate() {
        instructionList.add(
            new BasicInstruction("add $t1,$t2,$t3",
                "Add: set $t1 to ($t2 + $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000000 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a + b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("sub $t1,$t2,$t3",
                "Sub: set $t1 to ($t2 - $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000001 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a - b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("mul $t1,$t2,$t3",
                "Mul: set $t1 to ($t2 * $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000010 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a * b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("div $t1,$t2,$t3",
                "Div: set $t1 to ($t2 / $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000011 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        int res = 0;
                        if (b != 0) res = a / b;
                        RegisterFile.updateRegister(operands[0], res);
                    }
                }));

        instructionList.add(
            new BasicInstruction("and $t1,$t2,$t3",
                "Bitwise AND: set $t1 to ($t2 & $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000100 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a & b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("or $t1,$t2,$t3",
                "Bitwise OR: set $t1 to ($t2 | $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000101 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a | b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("xor $t1,$t2,$t3",
                "Bitwise XOR: set $t1 to ($t2 ^ $t3)",
                BasicInstructionFormat.R_FORMAT,
                "000110 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int a = RegisterFile.getValue(operands[1]);
                        int b = RegisterFile.getValue(operands[2]);
                        RegisterFile.updateRegister(operands[0], a ^ b);
                    }
                }));

        instructionList.add(
            new BasicInstruction("mov $t1,$t2",
                "Move: set $t1 to ($t2)",
                BasicInstructionFormat.R_FORMAT,
                "000111 sssss 00000 fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int val = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[0], val);
                    }
                }));

        
        instructionList.add(
            new BasicInstruction("li $t1, 100",
                "Load immediate: set $t1 to immediate",
                BasicInstructionFormat.I_FORMAT,
                "010000 00000 fffff ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[1] << 16 >> 16;
                        RegisterFile.updateRegister(operands[0], imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("beq $t1,$t2,label",
                "Branch if equal: if $t1 == $t2 branch to label/immediate",
                BasicInstructionFormat.I_BRANCH_FORMAT,
                "010001 sssss ttttt ffffffffffffffff",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int v1 = RegisterFile.getValue(operands[0]);
                        int v2 = RegisterFile.getValue(operands[1]);
                        if (v1 == v2) {
                            Globals.instructionSet.processBranch(operands[2]);
                        }
                    }
                }));

        instructionList.add(
            new BasicInstruction("dmg $t1,$t2",
                "Damage: subtract attack power ($t2) from target HP ($t1)",
                BasicInstructionFormat.R_FORMAT,
                "001000 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int targetHP = RegisterFile.getValue(operands[1]);
                        int attack = RegisterFile.getValue(operands[2]);
                        int critFlag = RegisterFile.getValue(8);
                        if (critFlag != 0) {
                            attack = attack * 2;
                            RegisterFile.updateRegister(8, 0);
                        }
                        int newHP = targetHP - attack;
                        if (newHP < 0) newHP = 0;
                        RegisterFile.updateRegister(operands[0], newHP);
                    }
                }));

        instructionList.add(
            new BasicInstruction("crit $t1,$t2",
                "Critical check: if $t2 > 50 then next damage to $t1 is doubled",
                BasicInstructionFormat.R_FORMAT,
                "001010 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int chance = RegisterFile.getValue(operands[2]);
                        if (chance > 50) RegisterFile.updateRegister(8, 1); else RegisterFile.updateRegister(8, 0);
                    }
                }));

        instructionList.add(
            new BasicInstruction("poison $t1",
                "Apply poison status to target ($t1)",
                BasicInstructionFormat.R_FORMAT,
                "001011 sssss 00000 fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int status = RegisterFile.getValue(7);
                        status = status | 0x1;
                        RegisterFile.updateRegister(7, status);
                    }
                }));

        instructionList.add(
            new BasicInstruction("burn $t1",
                "Apply burn status to target ($t1)",
                BasicInstructionFormat.R_FORMAT,
                "001100 sssss 00000 fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int status = RegisterFile.getValue(7);
                        status = status | 0x2;
                        RegisterFile.updateRegister(7, status);
                    }
                }));

        instructionList.add(
            new BasicInstruction("turn",
                "Increment battle counter (R11)",
                BasicInstructionFormat.R_FORMAT,
                "001101 00000 00000 01011 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int count = RegisterFile.getValue(11);
                        RegisterFile.updateRegister(11, count + 1);
                    }
                }));

        instructionList.add(
            new BasicInstruction("heal $t1, 100",
                "Heal: add immediate to HP in register $t1",
                BasicInstructionFormat.I_FORMAT,
                "010011 sssss fffff tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
                        int hp = RegisterFile.getValue(operands[0]);
                        RegisterFile.updateRegister(operands[0], hp + imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("stat_up $t1, 100",
                "Stat up: add immediate to given stat (rs)",
                BasicInstructionFormat.I_FORMAT,
                "010100 fffff 00000 ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[1] << 16 >> 16;
                        int current = RegisterFile.getValue(operands[0]);
                        RegisterFile.updateRegister(operands[0], current + imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("stat_down $t1, 100",
                "Stat down: subtract immediate from given stat (rs)",
                BasicInstructionFormat.I_FORMAT,
                "010101 fffff 00000 ssssssssssssssss",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[1] << 16 >> 16;
                        int current = RegisterFile.getValue(operands[0]);
                        RegisterFile.updateRegister(operands[0], current - imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("encounter $t1,$t2",
                "Encounter: finds a Pokemon and attempts catch. $t1 = pokeball type (0=Poke, 1=Great, 2=Ultra, 3=Master), $t2 = caught (output)",
                BasicInstructionFormat.R_FORMAT,
                "010110 sssss 00000 fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();

                        int pokeballType = RegisterFile.getValue(operands[0]);
                        int regCaught = operands[1];
                        
                        int regRarity = 10;

                        java.util.Random rng = new java.util.Random();

                        int roll = rng.nextInt(100);
                        int rarity;
                        if (roll < 40) rarity = 0;
                        else if (roll < 70) rarity = 1;
                        else if (roll < 90) rarity = 2;
                        else rarity = 3;

                        int baseCatchChance;
                        switch (rarity) {
                            case 0: baseCatchChance = 90; break;
                            case 1: baseCatchChance = 70; break;
                            case 2: baseCatchChance = 50; break;
                            case 3: baseCatchChance = 25; break;
                            default: baseCatchChance = 0;
                        }

                        double pokeballMultiplier;
                        String pokeballName;
                        switch (pokeballType) {
                            case 0: 
                                pokeballMultiplier = 1.0; 
                                pokeballName = "Poke Ball";
                                break;
                            case 1: 
                                pokeballMultiplier = 1.5; 
                                pokeballName = "Great Ball";
                                break;
                            case 2: 
                                pokeballMultiplier = 2.0; 
                                pokeballName = "Ultra Ball";
                                break;
                            case 3: 
                                pokeballMultiplier = 100.0;
                                pokeballName = "Master Ball";
                                break;
                            default: 
                                pokeballMultiplier = 1.0; 
                                pokeballName = "Poke Ball";
                                break;
                        }

                        int catchChance;
                        if (pokeballType == 3) {
                            catchChance = 100;
                        } else {
                            catchChance = (int)(baseCatchChance * pokeballMultiplier);
                            if (catchChance > 95) catchChance = 95;
                        }

                        int catchRoll = rng.nextInt(100);
                        int caught = (catchRoll < catchChance) ? 1 : 0;

                        RegisterFile.updateRegister(regRarity, rarity);
                        RegisterFile.updateRegister(regCaught, caught);

                        String rarityName;
                        switch (rarity) {
                            case 0: rarityName = "COMMON"; break;
                            case 1: rarityName = "UNCOMMON"; break;
                            case 2: rarityName = "RARE"; break;
                            case 3: rarityName = "LEGENDARY"; break;
                            default: rarityName = "UNKNOWN"; break;
                        }
                        mars.util.SystemIO.printString(
                            "You encountered a " + rarityName + " Pokemon!\n"
                        );
                        mars.util.SystemIO.printString(
                            "You used a " + pokeballName + "!\n"
                        );

                        if (caught == 1)
                            mars.util.SystemIO.printString("You caught it!\n");
                        else
                            mars.util.SystemIO.printString("It broke free...\n");
                    }
                }));


        instructionList.add(
            new BasicInstruction("init_inventory",
                "Initialize pokeball inventory: 10 Poke Balls, 8 Great Balls, 3 Ultra Balls, 1 Master Ball. Stores in R12-R15",
                BasicInstructionFormat.R_FORMAT,
                "011000 00000 00000 00000 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {

                        RegisterFile.updateRegister(12, 10);
                        RegisterFile.updateRegister(13, 8);
                        RegisterFile.updateRegister(14, 3);
                        RegisterFile.updateRegister(15, 1);
                        SystemIO.printString("Pokeball inventory initialized!\n");
                        SystemIO.printString("  Poke Balls: 10\n");
                        SystemIO.printString("  Great Balls: 8\n");
                        SystemIO.printString("  Ultra Balls: 3\n");
                        SystemIO.printString("  Master Balls: 1\n\n");
                    }
                }));


        instructionList.add(
            new BasicInstruction("encounter_input $t1",
                "Encounter with user input: reveals Pokemon rarity, shows inventory, prompts for pokeball (0-3) or run (4), then attempts catch. $t1 = caught (output)",
                BasicInstructionFormat.R_FORMAT,
                "010111 00000 00000 fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int regCaught = operands[0];
                        
                        int pokeBalls = RegisterFile.getValue(12);
                        int greatBalls = RegisterFile.getValue(13);
                        int ultraBalls = RegisterFile.getValue(14);
                        int masterBalls = RegisterFile.getValue(15);
                        
                        if (pokeBalls == 0 && greatBalls == 0 && ultraBalls == 0 && masterBalls == 0) {
                            pokeBalls = 10;
                            greatBalls = 8;
                            ultraBalls = 3;
                            masterBalls = 1;
                            RegisterFile.updateRegister(12, 10);
                            RegisterFile.updateRegister(13, 8);
                            RegisterFile.updateRegister(14, 3);
                            RegisterFile.updateRegister(15, 1);
                        }

                        java.util.Random rng = new java.util.Random();
   
                        int roll = rng.nextInt(100);
                        int rarity;
                        if (roll < 40) rarity = 0;
                        else if (roll < 70) rarity = 1;
                        else if (roll < 90) rarity = 2;
                        else rarity = 3;

                        String rarityName;
                        switch (rarity) {
                            case 0: rarityName = "COMMON"; break;
                            case 1: rarityName = "UNCOMMON"; break;
                            case 2: rarityName = "RARE"; break;
                            case 3: rarityName = "LEGENDARY"; break;
                            default: rarityName = "UNKNOWN"; break;
                        }
                        
                        SystemIO.printString("\nPOKEMON ENCOUNTER: \n");
                        SystemIO.printString("You encountered a " + rarityName + " Pokemon!\n");
                        SystemIO.printString("\n Your Inventory: \n");
                        SystemIO.printString("  Poke Balls: " + pokeBalls + "\n");
                        SystemIO.printString("  Great Balls: " + greatBalls + "\n");
                        SystemIO.printString("  Ultra Balls: " + ultraBalls + "\n");
                        SystemIO.printString("  Master Balls: " + masterBalls + "\n");
                        SystemIO.printString("\nChoose your action:\n");
                        
                        if (pokeBalls > 0) {
                            SystemIO.printString("  0 = Poke Ball   (1.0x catch rate) - Available: " + pokeBalls + "\n");
                        } else {
                            SystemIO.printString("  0 = Poke Ball   - OUT OF STOCK\n");
                        }
                        if (greatBalls > 0) {
                            SystemIO.printString("  1 = Great Ball  (1.5x catch rate) - Available: " + greatBalls + "\n");
                        } else {
                            SystemIO.printString("  1 = Great Ball  - OUT OF STOCK\n");
                        }
                        if (ultraBalls > 0) {
                            SystemIO.printString("  2 = Ultra Ball  (2.0x catch rate) - Available: " + ultraBalls + "\n");
                        } else {
                            SystemIO.printString("  2 = Ultra Ball  - OUT OF STOCK\n");
                        }
                        if (masterBalls > 0) {
                            SystemIO.printString("  3 = Master Ball (100% catch rate) - Available: " + masterBalls + "\n");
                        } else {
                            SystemIO.printString("  3 = Master Ball - OUT OF STOCK\n");
                        }
                        SystemIO.printString("  4 = Run Away (don't use any balls)\n");
                        SystemIO.printString("Enter your choice (0-4): ");
   
                        String input = SystemIO.readString(8, 10);
                        int pokeballType = -1;
                        boolean ranAway = false;
                        try {
                            int choice = Integer.parseInt(input.trim());
                            if (choice == 4) {
                                ranAway = true;
                                pokeballType = -1;
                            } else if (choice >= 0 && choice <= 3) {
                                pokeballType = choice;
                            } else {
                                SystemIO.printString("Invalid choice! Running away...\n");
                                ranAway = true;
                            }
                        } catch (NumberFormatException e) {
                            SystemIO.printString("Invalid input! Running away...\n");
                            ranAway = true;
                        }
                        
                        if (ranAway) {
                            RegisterFile.updateRegister(10, rarity);
                            RegisterFile.updateRegister(regCaught, 0);
                            SystemIO.printString("You ran away from the " + rarityName + " Pokemon!\n");
                            return;
                        }
                        
                        boolean available = false;
                        switch (pokeballType) {
                            case 0: available = (pokeBalls > 0); break;
                            case 1: available = (greatBalls > 0); break;
                            case 2: available = (ultraBalls > 0); break;
                            case 3: available = (masterBalls > 0); break;
                        }
                        
                        if (!available) {
                            SystemIO.printString("You don't have that pokeball! Running away...\n");
                            RegisterFile.updateRegister(10, rarity);
                            RegisterFile.updateRegister(regCaught, 0);
                            return;
                        }
                        
                        switch (pokeballType) {
                            case 0: 
                                pokeBalls--;
                                RegisterFile.updateRegister(12, pokeBalls);
                                break;
                            case 1: 
                                greatBalls--;
                                RegisterFile.updateRegister(13, greatBalls);
                                break;
                            case 2: 
                                ultraBalls--;
                                RegisterFile.updateRegister(14, ultraBalls);
                                break;
                            case 3: 
                                masterBalls--;
                                RegisterFile.updateRegister(15, masterBalls);
                                break;
                        }
                        
                        int baseCatchChance;
                        switch (rarity) {
                            case 0: baseCatchChance = 90; break;
                            case 1: baseCatchChance = 70; break;
                            case 2: baseCatchChance = 50; break;
                            case 3: baseCatchChance = 25; break;
                            default: baseCatchChance = 0;
                        }

                        double pokeballMultiplier;
                        String pokeballName;
                        switch (pokeballType) {
                            case 0: 
                                pokeballMultiplier = 1.0; 
                                pokeballName = "Poke Ball";
                                break;
                            case 1: 
                                pokeballMultiplier = 1.5; 
                                pokeballName = "Great Ball";
                                break;
                            case 2: 
                                pokeballMultiplier = 2.0; 
                                pokeballName = "Ultra Ball";
                                break;
                            case 3: 
                                pokeballMultiplier = 100.0;
                                pokeballName = "Master Ball";
                                break;
                            default: 
                                pokeballMultiplier = 1.0; 
                                pokeballName = "Poke Ball";
                                break;
                        }
                        
                        int catchChance;
                        if (pokeballType == 3) {
                            catchChance = 100;
                        } else {
                            catchChance = (int)(baseCatchChance * pokeballMultiplier);
                            if (catchChance > 95) catchChance = 95;
                        }
                        
                        int catchRoll = rng.nextInt(100);
                        int caught = (catchRoll < catchChance) ? 1 : 0;

                        RegisterFile.updateRegister(10, rarity);
                        RegisterFile.updateRegister(regCaught, caught);

                        SystemIO.printString("\nYou used a " + pokeballName + "!\n");
                        if (caught == 1)
                            SystemIO.printString("You caught it!\n");
                        else
                            SystemIO.printString("It broke free...\n");
                        SystemIO.printString("\nRemaining inventory:\n");
                        SystemIO.printString("  Poke Balls: " + pokeBalls + "\n");
                        SystemIO.printString("  Great Balls: " + greatBalls + "\n");
                        SystemIO.printString("  Ultra Balls: " + ultraBalls + "\n");
                        SystemIO.printString("  Master Balls: " + masterBalls + "\n");
                       
                    }
                }));

    }

}
