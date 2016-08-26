# Server configuration

Angular2 application by default works with so called hashbangs. They made url look like `/#/window/111`. To change this to `/window/111` you need to use HTML5 browser history functionality and adjust your server configuration.

## Apache2

Server configuration for Apache2 can be found in project root in `.htaccess`.

## nginx

In your `server {}` block use:

```
    root /var/www/root_project_dir;
    index index.html;

    location / {
        try_files $uri /index.html;
    }
```
