package cs6367.Coverage.CodeFiles;



import cs6367.Coverage.MetricsCalculation;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
*
* @goal metrics
* @phase process-classes
* @requiresDependencyResolution runtime
*/
public class CodeforClassfiles extends AbstractMojo {
	 /**
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    
    private File Directory;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Files(Directory);
            MetricsCalculation.print();
        } catch (IOException e) {
           System.out.println("Error processing class files");
        }
    }

    private void Files(File classesDir) throws IOException {
        for (File f : classesDir.listFiles()) {
            if (f.isDirectory())
                Files(f);
            else
            if (f.getName().endsWith(".class")) {
              
                    calculation(f);
            }
        }
    }

    private void calculation(File f) throws IOException {
        try (InputStream inputstream = new FileInputStream(f)) {

            
            ClassReader reader = new ClassReader(inputstream);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            ClassTransform visitor = new ClassTransform(writer);
            reader.accept(visitor, 0);
            writer.toByteArray();
        }
    }

}
