
# Metasfresh Front-end Application


## For webui-frontend developers


### Init
- Install dependencies
> npm install


- Create config. In that case run:
> cp config.js.dist config.js

### Dev environment

- install npm and node.js

- make sure you have all dependencies by:
> npm install

- Then remember of creating config:
> cp /config.js.dist /config.js

- Then you should run node server by:
> npm start

### Production environment
When running in production mode you will need to build the static version of the app and serve it from an http-compatible server. Here's a quick guide how you can run production mode locally.

#### Building
In case of static version building execute (you are going need [Webpack](https://www.npmjs.com/package/webpack) installed [globally](https://webpack.js.org/guides/installation/#global-installation)):
> webpack --config webpack.prod.js

And after that we need `config.js` in `dist` folder
> cp /config.js.dist /dist/

#### Running
The easiest way to test production build is by serving it via a simple [http-server](https://www.npmjs.com/package/http-server). You can install it globally with npm :
> npm install http-server -g

and then run it pointing to your dist folder:
> http-server ./dist

Now open your browser and go to `localhost:8080` to see the application running.


### Testing
Application comes with a set of tests, both unit as well as functional.

#### Cypress e2e tests

Actual cypress tests reside in the [metasfresh](https://github.com/metasfresh/metasfresh) repo. (`e2e`-folder)<br>

### Contribution

Remember to ensure before contribution that your IDE supports `.editorconfig` file,
and if needed fix your file before commit changes.

Also remember to respect our code-schema rules. All of them are listed in __eslint__ and __stylelint__ config files. To use them, just run:
> npm run-script lint
> 
> npm run-script stylelint

(first one is also autofixing when possible) <br />
Also please follow our [Code of conduct](https://github.com/metasfresh/metasfresh/wiki/Code-of-conduct-for-webui-frontend-contributors)

### Dictionary

Project has a generic structure. Name of components and their containers should be strictly defined and keep for better understanding.

__MasterWindow__ - (e.g. `/window/143/1000000`) It is the container for displaying a single document view (document main view, detailed view).

__DocList__ - (e.g. `/window/143/`) It's a view with a list of documents kept in table.

__DocumentList__ - It is a component that combining table for documents, filters, selection attributes, etc...

__Window__ - It is a component that is generating a set of sections, columns, elements groups, elements lines and widgets (these are defined by the backend layout)

__Widget__ - (__MasterWidget__, __RawWidget__) It is a component for getting user input.

__Header__ - It is a top navbar with logo.

__Subheader__ - It is a part of __Header__ and is toggled by *button with a home icon*.

__Sidelist__ - Toggled by *button with hamburger menu icon* in __Header__. It is collapsing panel situated on right side of 'browser window'.

__MenuOverlay__ - These are components that float over __Header__ and contain navigation links, triggered from breadcrumb.

__SelectionAttributes__ - It is a panel that might contain __Widgets__ and it is a side by side table in __DocumentList__.

### Schema
__MasterWindow__

Class based component (`containers/`).
- BlankPage
- Container
- Overlay
- Window

__BlankPage__

Class based component (`components/`). Returns the 404 with a reason in case the url was not found.

__Container__

Class based component (`components/`).
- DocumentList
- ErrorScreen
- Modal
- RawModal
- Header

__Overlay__

Class based component (`components/app/`).
- QRCode

__Window__

Class based component (`components/`). The window component creates the general layout for a frontend UI based on sections, columns, elementgroups, elementlines and elements. The layout information is received from the backend API.
- Dropzone
- EntryTable
- MasterWidget
- Separator
- Table
- TableContextShortcuts
- Tabs
- Tooltips

__DocumentList__

Class based component (`components/app/`). The DocumentList component shows all records in a Main Table View. This View are always shown when opening any window without a record ID, e.g. opened when clicking on menu entry or via document reference.
- BlankPage
- DataLayoutWrapper
- Filters
- FiltersStatic
- Table
- QuickActions
- SelectionAttributes
- Spinner

__ErrorScreen__

Class based component (`components/app/`). This view is shown when the connection to the backend is lost or other connection issues appear.

__Modal__

Class based component (`components/app/`). This is an overlay view that can be opened over the main view.
- ChangeLogModal
- Indicator
- ModalContextShortcuts
- OverlayField
- Process
- Tooltips
- Window

__RawModal__

Class based component (`components/app/`).
- Indicator
- ModalContextShortcuts
- Tooltips

__Header__

Class based component (`components/header/`). The Header component is shown in every view besides Modal or RawModal in frontend. It defines the top bar with different menus and icons in metasfresh WebUI. It hosts the action menu, breadcrumb, logo, notification menu, avatar and sidelist menu.
- Breadcrumb
- GlobalContextShortcuts
- Inbox
- Indicator
- MasterWidget
- NewEmail
- NewLetter
- Prompt
- Tooltips
- SideList
- Subheader
- UserDropdown

__QRCode__

Class based component (`components/app/`). The QRCode components allows to read QR Codes via webcam.

__Dropzone__

Class based component (`components/`). The DropzoneWrapper allows the user to drag&drop files onto the UI. These files are then uploaded to metasfresh backend.

__EntryTable__

Class based component (`components/table/`).
- MasterWidget
- WidgetTooltip

__MasterWidget__

Class based component (`components/widget/`).
- RawWidget

__Separator__

Function based component (`components/`). The separator component allows to create collapsible sections in the window component. The separators can  have a title. It is used as layout and grouping element in WebUI.

__Table__

Class based component (`components/table/`). This component represents the generic table views shown as default for all windows without record ID. Is rendered via the document list component.
- DocumentListContextShortcuts
- Prompt
- TableContextShortcuts
- TableContextMenu
- TableFilter
- TableHeader
- TableItem
- TablePagination

---

- DocList
  - Container
  - DocumentList
- Header
  - Subheader
  - Sidelist
  - Breadcrumb
    - MenuOverlay
- Modal
  - Window
  - Process
- RawModal
  - DocumentList

## For webui-api developers

If you are developing against the [metasfresh-webui-api](https://github.com/metasfresh/metasfresh-webui-api), 
you might want to run the webui-frontend without locally installing node and npm.
If you have a docker host, you can do so by checking out this repository and then following the instructions in the docker file `docker/nginx/Dockerfile`.
