import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author coryp_000
 */
public class Filter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith("jpg") || file.getName().endsWith("JPG") || file.getName().endsWith("png");
    }
    
}