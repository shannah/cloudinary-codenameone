package com.codename1.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.File;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.codename1.capture.Capture;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class CloudinaryDemo {

    private Form current;
    private Resources theme;
    Cloudinary cloudinary;
    
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        cloudinary = new Cloudinary("cloudinary://527557556819548:nbBxRe0KE79TYow2cIAl8PFxoe4@codename-one");
        cloudinary.config.privateCdn = false;
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });*/
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        Image placeholder = Image.createImage(Display.getInstance().convertToPixels(20, true),
                Display.getInstance().convertToPixels(20, false));
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        
        
        Form hi = new Form("Hi World");
        hi.addComponent(new Label("Hi World"));
        /*
        String url = cloudinary.url().format("jpg")
  .transformation(new Transformation().width(encImage.getWidth()).height(encImage.getHeight()).crop("fill"))
  
                .generate("sample");
        */
        //System.out.println("Url is "+url);
        //URLImage img = URLImage.createToStorage(encImage, url, url, null);
        Image img = cloudinary.url()
                .format("png")
                
                .transformation(new Transformation().crop("fill").radius(Display.getInstance().convertToPixels(3, true)))
                
                .image(encImage, "sample");
        hi.addComponent(new Label(img));
        
        
        Image img2 = cloudinary.url()
                .type("fetch")
                .cacheName("Jennifer_Lawrence_at_the_83rd_Academy_Awards.jpg")
                .format("png")
                .transformation(new Transformation().crop("fill").radius(Display.getInstance().convertToPixels(3, true)))
                .image(encImage, "http://upload.wikimedia.org/wikipedia/commons/4/46/Jennifer_Lawrence_at_the_83rd_Academy_Awards.jpg");
        hi.addComponent(new Label(img2));
        Button b = new Button("Upload Image");
        b.addActionListener((evt)-> {
            String path = Capture.capturePhoto(1000, 1000);
            try {
                cloudinary.uploader().upload(new File(path), ObjectUtils.emptyMap());
            } catch (IOException ex) {
                Log.e(ex);
            }
            System.out.println("Complete");
        });
        
        hi.addComponent(b);
        
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

}
