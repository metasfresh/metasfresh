The settings here should be copied into the `.idea/` folder of your project.

Note that if you have multiple `.idea/` folders, you imported the project incorrectly. There should only be a single `.idea/` folder and the directory tree should look as follows:

```    
C:\work-metas
             \.idea       <- this is the only .idea folder that should exist in your project
             \metasfresh
             \metasfresh-dev
             \metasfresh-dist
             \metasfresh-documentation
             \metasfresh-parent
             \metasfresh-procurement-webui
             \metasfresh-webui-api
             \metasfresh-webui-frontend
```

If there are duplicate conflicts, you should merge the files there by using idea's [files comparator](https://www.jetbrains.com/help/idea/comparing-files-and-folders.html) to diff between the files already in the project's `.idea/` folder and this folder here.

### Reference


- [How to manage projects under Version Control Systems - JetBrains Support](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839-How-to-manage-projects-under-Version-Control-Systems)
