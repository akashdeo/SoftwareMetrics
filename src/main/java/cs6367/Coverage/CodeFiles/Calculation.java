package cs6367.Coverage.CodeFiles;

import cs6367.Coverage.MetricsCalculation;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.Textifier;

import java.util.Arrays;
import java.util.List;

public class Calculation extends Textifier {

    List<String> operators = Arrays.asList(("IADD,LADD,"
                    + "FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,"
                    + "FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,"
                    + "ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,IF_ICMPNE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE").split(","));
    List<String> jumpInstructions= Arrays.asList(("IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,IFNULL,IFNONNULL").split(","));
    List<String> zerooperands=Arrays.asList(("NOP, ACONST_NULL, ICONST_M1, ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5, LCONST_0, LCONST_1, FCONST_0, FCONST_1, FCONST_2, DCONST_0, DCONST_1, IALOAD, LALOAD, FALOAD, DALOAD, AALOAD, BALOAD, CALOAD, SALOAD, IASTORE, LASTORE, FASTORE, DASTORE, AASTORE, BASTORE, CASTORE, SASTORE, POP, POP2, DUP, DUP_X1, DUP_X2, DUP2, DUP2_X1, DUP2_X2, SWAP, IADD, LADD, FADD, DADD, ISUB, LSUB, FSUB, DSUB, IMUL, LMUL, FMUL, DMUL, IDIV, LDIV, FDIV, DDIV, IREM, LREM, FREM, DREM, INEG, LNEG, FNEG, DNEG, ISHL, LSHL, ISHR, LSHR, IUSHR, LUSHR, IAND, LAND, IOR, LOR, IXOR, LXOR, I2L, I2F, I2D, L2I, L2F, L2D, F2I, F2L, F2D, D2I, D2L, D2F, I2B, I2C, I2S, LCMP, FCMPL, FCMPG, DCMPL, DCMPG, IRETURN, LRETURN, FRETURN, DRETURN, ARETURN, RETURN, ARRAYLENGTH, ATHROW, MONITORENTER, MONITOREXIT").split(","));
    String methodRunning;
    String classRunning;
   
    public Calculation(final int api) {
        super(api);
    }

    public Calculation() {
        this(Opcodes.ASM5);
        if (getClass() != Calculation.class) {
            throw new IllegalStateException();
        }
    }
  
    @Deprecated
    @Override
    public void visitMethodInsn(final int opcode, final String owner,
            final String name, final String desc) {
        if (api >= Opcodes.ASM5) {
            super.visitMethodInsn(opcode, owner, name, desc);
            return;
        }
        doVisitMethodInsn(opcode, owner, name, desc,
                opcode == Opcodes.INVOKEINTERFACE);
    }

    @Override
    public void visitMethodInsn(final int opcode, final String owner,
            final String name, final String desc, final boolean itf) {
        if (api < Opcodes.ASM5) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        doVisitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        MetricsCalculation.methodVisit(owner+"."+name,methodRunning,"Variables Referenced:");
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        MetricsCalculation.methodVisit(type, methodRunning,"Exceptions Found:");
        super.visitTryCatchBlock(start, end, handler, type);
    }
    

    private void doVisitMethodInsn(final int opcode, final String owner,
            final String name, final String desc, final boolean itf) {
        String ownerClass = owner.replaceAll("/",".");
        if(ownerClass.equals(classRunning)){
            MetricsCalculation.methodVisit(classRunning+"."+name,methodRunning,"No. of local methods:");
        }else{
            MetricsCalculation.methodVisit(classRunning+"."+name,methodRunning,"No. of external methods:");
        }

        if(OPCODES[opcode].equals("INVOKESTATIC")){
            MetricsCalculation.methodVisit(ownerClass,methodRunning,"Class References:");
        }
    }

    @Override public void visitTypeInsn(int opcode, String type) {
        if(OPCODES[opcode].equals("NEW")){
            MetricsCalculation.methodVisit(type.replaceAll("/","."),methodRunning,"Class References:");
            MetricsCalculation.methodVisit(type.replaceAll("/","."),methodRunning, "No. of expressions:");
        }
        if(OPCODES[opcode].equals("INSTANCEOF")){
            MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of operators:");
        }
        if(OPCODES[opcode].equals("CHECKCAST")){
            MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of casts:");
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override public void visitInsn(int opcode) {
        if(OPCODES[opcode].contains("STORE")){
            MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of expressions:");
            MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of operators:");
        }
		if(zerooperands.contains(OPCODES[opcode])){
			MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of Operands:");
		}
        if(operators.contains(OPCODES[opcode])){
            MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of operators:");
        }
        super.visitInsn(opcode);
    }

    @Override
    public Textifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        classRunning = name.substring(0,name.lastIndexOf('.'));
        methodRunning = name;

        MetricsCalculation.methodVisit(String.valueOf(Type.getArgumentTypes(desc).length),name,"No. of Arguments:");

        String modifiers = appendAccess(access & ~Opcodes.ACC_VOLATILE);
        MetricsCalculation.methodVisit(modifiers,name,"Modifier type:");
        if (exceptions != null && exceptions.length > 0) {
            MetricsCalculation.methodVisit(String.valueOf(exceptions.length),name,"No. of Exceptions Thrown:");
        }
        Textifier t = new Calculation();
        return t;
    }



    @Override
    public void visitLineNumber(int line, Label start) {
        MetricsCalculation.methodVisit(String.valueOf(line), methodRunning,"No. of Java Statements:");
        super.visitLineNumber(line, start);
    }
    @Override
    public void visitIntInsn(int opcode,int operand)
    {
    	if((operand>=Byte.MIN_VALUE&&operand<=Byte.MAX_VALUE)||(operand>=Short.MIN_VALUE&&operand<=Short.MAX_VALUE)||(operand==Opcodes.T_BOOLEAN)||(operand==Opcodes.T_CHAR)||(operand==Opcodes.T_FLOAT)||(operand==Opcodes.T_DOUBLE)||(operand==Opcodes.T_BYTE)||(operand==Opcodes.T_INT)||(operand==Opcodes.T_LONG)||(zerooperands.contains(OPCODES[opcode])) )
    	{
    		MetricsCalculation.methodVisit(String.valueOf(opcode), methodRunning, "No. of Operands:");
    	}
    	
    }
    @Override
    public void visitLocalVariable(String name,
            String desc,
            String signature,
            Label start,
            Label end,
            int index)
    {
    	MetricsCalculation.methodVisit(String.valueOf(index), methodRunning,"Variables Declared:");
    }
    @Override
    public void visitJumpInsn(int opcode,Label label)
    {
    	if(jumpInstructions.contains(OPCODES[opcode]))
    	{
    		MetricsCalculation.methodVisit(String.valueOf(opcode),methodRunning, "No. of jump instructions:");
    	}
    }
    
    
    
    
    
    
    
    private String appendAccess(final int access) {
        String result = "";
        int f=0;
        if ((access & Opcodes.ACC_PUBLIC) != 0) {
            result = "public";
            buf.append("public ");
            f=1;
        }
        if ((access & Opcodes.ACC_PRIVATE) != 0) {
            result = "private";
            buf.append("private ");
            f=1;
        }
        if ((access & Opcodes.ACC_PROTECTED) != 0) {
            result = "protected";
            buf.append("protected ");
            f=1;
        }
        if ((access & Opcodes.ACC_FINAL) != 0) {
            result = "final";
            buf.append("final ");
            f=1;
        }
        if ((access & Opcodes.ACC_STATIC) != 0) {
            result = "static";
            buf.append("static ");
            f=1;
        }
        if ((access & Opcodes.ACC_SYNCHRONIZED) != 0) {
            result = "synchronized";
            buf.append("synchronized ");
            f=1;
        }
        if ((access & Opcodes.ACC_VOLATILE) != 0) {
            result = "volatile";
            buf.append("volatile ");
            f=1;
        }
        if ((access & Opcodes.ACC_TRANSIENT) != 0) {
            result = "transient";
            buf.append("transient ");
            f=1;
        }
        if ((access & Opcodes.ACC_ABSTRACT) != 0) {
            result = "abstract";
            buf.append("abstract ");
            f=1;
        }
        
        if ((access & Opcodes.ACC_STRICT) != 0) {
            result = "strict";
            buf.append("strict ");
            f=1;
        }
        if ((access & Opcodes.ACC_SYNTHETIC) != 0) {
            result = "synthetic";
            buf.append("synthetic ");
            f=1;
        }
        if ((access & Opcodes.ACC_MANDATED) != 0) {
            result = "mandated";
            buf.append("mandated ");
            f=1;
        }
        if ((access & Opcodes.ACC_ENUM) != 0) {
            result = "enum";
            buf.append("enum ");
            f=1;
        }
        if(f==0)
        	result="no modifier";
        
        return result;
    }
}

