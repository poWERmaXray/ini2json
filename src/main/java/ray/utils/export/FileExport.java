package ray.utils.export;

import java.io.File;

public interface FileExport {
    File exportJSONFile(File file, File outputFile, Object genericObject);
}
