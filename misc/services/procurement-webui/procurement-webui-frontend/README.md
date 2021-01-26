# Procurement App

# Technologies used

* react (>= 17.0.1)
* react-swipeable (>=6.0.1)
* typescript (>= 4.1.2)
* mobx-state-tree (>= 3.6.2)
* bulma (>=0.9.1)
* redis

# Running the app for dev

Clone the repo

`git clone https://github.com/metasfresh/metasfresh.git`

Go to the Procurement-app folder

`cd metasfresh/misc/services/procurement-webui/procurement-webui-frontend/`

Install the dependencies

`yarn install`

Export the DEV_SERVER env variable with the URI of your BE running server

`export DEV_SERVER=https://remotehost.com/`

Run the app

`yarn start`

Access in the browser the app on this URI:

`http://localhost:3000/`

# Running the app for prod

Install the dependencies

`yarn install`

Export the public url where the app will reside

`export PUBLIC_URL=https://www.example.com/`

Build the app

`yarn build`

The structure can be served with whatever server you want nginx/Apache.


You also have a Dockerfile in the root of the structure in case you want to run this in a container.





