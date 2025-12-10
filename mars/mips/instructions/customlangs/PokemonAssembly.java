package mars.mips.instructions.customlangs;
    import mars.mips.hardware.*;
    import mars.*;
    import mars.mips.instructions.*;

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
                "010000 00000 fffff tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
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
            new BasicInstruction("dmg_taken $t1,$t2",
                "Damage taken: subtract defense ($t2) from target HP ($t1)",
                BasicInstructionFormat.R_FORMAT,
                "001001 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int targetHP = RegisterFile.getValue(operands[1]);
                        int defense = RegisterFile.getValue(operands[2]);
                        int newHP = targetHP - defense;
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
            new BasicInstruction("hp_up $t1, 100",
                "HP up: add immediate to HP (R1)",
                BasicInstructionFormat.I_FORMAT,
                "010010 00001 00001 tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
                        int hp = RegisterFile.getValue(1);
                        RegisterFile.updateRegister(1, hp + imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("heal $t1, 100",
                "Heal: add immediate to HP (R1)",
                BasicInstructionFormat.I_FORMAT,
                "010011 00001 00001 tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
                        int hp = RegisterFile.getValue(1);
                        RegisterFile.updateRegister(1, hp + imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("stat_up $t1, 100",
                "Stat up: add immediate to given stat (rs)",
                BasicInstructionFormat.I_FORMAT,
                "010100 sssss 00000 tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
                        int current = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[1], current + imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("stat_down $t1, 100",
                "Stat down: subtract immediate from given stat (rs)",
                BasicInstructionFormat.I_FORMAT,
                "010101 sssss 00000 tttttttttttttttt",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int imm = operands[2] << 16 >> 16;
                        int current = RegisterFile.getValue(operands[1]);
                        RegisterFile.updateRegister(operands[1], current - imm);
                    }
                }));

        instructionList.add(
            new BasicInstruction("show_battle $t1,$t2",
                "Display ASCII battle based on IDs in $t1 and $t2",
                BasicInstructionFormat.R_FORMAT,
                "001110 sssss ttttt fffff 00000 000000",
                new SimulationCode() {
                    public void simulate(ProgramStatement statement) throws ProcessingException {
                        int[] operands = statement.getOperands();
                        int id1 = RegisterFile.getValue(operands[0]);
                        int id2 = RegisterFile.getValue(operands[1]);
                        PokemonArtPrinter.printBattle(id1, id2);
                    }
                }));
    }

}
