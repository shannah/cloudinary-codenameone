# Cloudinary Codename One

This library adds support for the [Cloudinary](http://cloudinary.com/) image management service to [Codename One](http://www.codenameone.com) apps.  The library itself is a direct port of the [Cloudinary Java SDK](http://cloudinary.com/documentation/java_integration).

## Supported Platforms

Should work on all platforms that Codename One apps can be deployed to:

* iOS
* Android
* BlackBerry
* Windows Phone
* JavaSE / Desktop /Simulator
* HTML5

## Installation

1. Download the [latest release of cloudinary-codenameone.cn1lib](https://github.com/shannah/cloudinary-codenameone/releases/latest) and copy it into your project's "lib" directory.
2. Download [BouncyCastleCN1Lib.cn1lib](https://github.com/chen-fishbein/bouncy-castle-codenameone-lib/raw/master/BouncyCastleCN1Lib.cn1lib) and copy it into your project's "lib" directory.
2. Select "Codename One" > "Refresh Libs"

## Configuration

Refer to the [Cloudinary Java Integration Guide's Configuration Section](http://cloudinary.com/documentation/java_integration#configuration) for configuration information.  

My preferred configuration in a Codename One app is to place the following inside my app's `init()` method:

```java
import com.cloudinary.Cloudinary;

...
Cloudinary cloudinary;

...

public void init() {

    ...
    
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", "my_cloud_name",
      "api_key", "my_api_key",
      "api_secret", "my_api_secret"));
      
      
    // Disable private CDN URLs as this doesn't seem to work with free accounts
    cloudinary.config.privateCdn = false;
    
    ...
}
```

## Usage

**Sample Loading an Image**

```java
// We create a placeholder image to set the size for the image to fit
// to.
Image placeholder = Image.createImage(deviceWidth, deviceWidth);
EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);

// Load an image from cloudinary
Image img2 = cloudinary.url()
        .type("fetch")  // Says we are fetching an image
        .format("jpg")  // We want it to be a jpg
        .transformation(
            new Transformation().crop("fill")  //  We crop the image to fill the given width/height
                .width(deviceWidth)  
                .height(deviceWidth)
        )
        .image(encImage, "http://upload.wikimedia.org/wikipedia/commons/4/46/Jennifer_Lawrence_at_the_83rd_Academy_Awards.jpg");

// Add the image to a label and place it on the form.
Label l = new Label(img2);

```

**Alternatively Using Async API**

```java
cloudinary.url()
    .transformation(new Transformation().crop("fill").width(100).height(100))
    .format("jpg")
    .type("fetch")
    .image("http://upload.wikimedia.org/wikipedia/commons/4/46/Jennifer_Lawrence_at_the_83rd_Academy_Awards.jpg", (img3)->{
        Label l3 = new Label(img3);
        f.addComponent(l3);
        f.revalidate();
    });

```

## License

Released under the MIT license. 

## Documentation

* [JavaDocs](http://shannah.github.io/cloudinary-codenameone/javadoc/)
* [Cloudinary Java Integration Guide](http://cloudinary.com/documentation/java_integration) - Most of this should be applicable to the Codename One Library
* [Cloudinary Documentation Main Page](http://cloudinary.com/documentation/)
* [Demo Codename One App](https://github.com/shannah/cloudinary-codenameone/blob/master/src/com/codename1/cloudinary/CloudinaryDemo.java)

## Building From Source

~~~~
$ git clone https://github.com/shannah/cloudinary-codenameone.git
$ cd cloudinary-codenameone
$ ant -f configure.xml
$ ant jar
~~~~

You'll find `cloudinary-codenameone.cn1lib` inside the `dist` directory.

NOTE:  The `ant -f configure.xml` is only necessary the first time you build the source.  It will download some dependencies
like JavaSE.jar, CodenameOne.jar, etc.. that were not included with this repository to save space.

