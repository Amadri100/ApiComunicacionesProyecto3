package ImagenSerializable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author andre
 */
public class ImagenSerializable implements Serializable{
    private byte[] datosImagen;
    private String extensionArchivo;

    public ImagenSerializable(String ruta) {
        
    }
    
    public ImagenSerializable(BufferedImage imagen, String extension) throws IOException {
        this.extensionArchivo = extension;
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ImageIO.write(imagen, extension, byteOutput);
        this.datosImagen = byteOutput.toByteArray();
    }
    
    public ImagenSerializable(Path ruta) throws IOException {
        this(ImageIO.read(ruta.toFile()), getExtension(ruta));
    }
    
    public BufferedImage conseguirImagen() throws IOException {
        ByteArrayInputStream output = new ByteArrayInputStream(this.datosImagen);
        return ImageIO.read(output);
    }
    
    public static String getExtension(Path ruta) {
        String extension = "";
        String nombreArchivo = ruta.getFileName().toString();
        int indice = nombreArchivo.indexOf(".");
        if (indice != -1) {
            for (int i = indice +1 ; i < nombreArchivo.length(); i++) {
                extension += nombreArchivo.charAt(i);
            }
        }
        else {
            //
        }
        return extension;
    }

    public byte[] getDatosImagen() {
        return datosImagen;
    }

    public void setDatosImagen(byte[] datosImagen) {
        this.datosImagen = datosImagen;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }
    
}
