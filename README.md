# Cloudinary Codename One

This library adds support for the [Cloudinary](http://cloudinary.com/) image management service to Codename One apps.  The library itself is a direct port of the [Cloudinary Java SDK](http://cloudinary.com/documentation/java_integration).

## Installation

1. Download the latest release of cloudinary-codenameone.cn1lib and copy it into your project's "lib" directory.
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

