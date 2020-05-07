(Thanks to [wiadev](https://github.com/wiadev) for the following explanation)

**Here's what to do with fonts:**
1. We have single font file - Metasfresh, located in src/assets/css/fonts. There 4 formats, which are specific to various browsers/environments. In fact they all could be re-generated from one source.
1. Mapping between font glyphs (which are in fact SVGs) set in src/assets/css/font-meta.css. As you can see each icon corresponds to specific character code in that font.
1. To insert new you have to use one of available glyph editors, for example https://icomoon.io/app/#/select. So you're uploading the existing file (most of such tools accept SVG only type of font at import, so use src/assets/css/fonts/metasfresh.svg file to import your existing glyphs). Then append new icons, you can grab existing items from local machine or from gallery of available icons at icomoon.io (other tools usually has similar feature)
1. Then you have to save your updated font and make corresponding mapping between newly added glyphs and CSS file. Some chances that after exporting character codes might be changed for some reasons, so you have to check previously existing glyphs as well.
1. Then use new CSS classes in the app
