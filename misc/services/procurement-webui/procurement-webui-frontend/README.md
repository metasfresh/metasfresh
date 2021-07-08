# <img src='/images/metasfresh-logo-slogan-969x248.png' height='60' alt='metasfresh Logo - We do Open Source ERP' aria-label='metasfresh.com' /></a>
[![license](https://img.shields.io/badge/license-GPL-blue.svg)](https://github.com/metasfresh/metasfresh/blob/master/LICENSE.md)
# Procurement App

# Technologies used

* pwa
* react (>= 17.0.1)
* react-swipeable (>=6.0.1)
* typescript (>= 4.1.2)
* mobx-state-tree (>= 3.6.2)
* bulma (>=0.9.1)

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


# App structure

```bash
src
├── App.tsx
├── api
│   └── index.ts
├── components
│   ├── BottomNav.tsx
│   ├── Daily.tsx
│   ├── DailyNav.tsx
│   ├── Error404.tsx
│   ├── Header.tsx
│   ├── Info.tsx
│   ├── Login.tsx
│   ├── PasswordRecovery.tsx
│   ├── Product.tsx
│   ├── ProductAdd.tsx
│   ├── ProductAddItem.tsx
│   ├── ProductAddList.tsx
│   ├── ProductList.tsx
│   ├── ProductScreen.tsx
│   ├── ProductWeeklyEdit.tsx
│   ├── ProductWeeklyScreen.tsx
│   ├── Prognose.tsx
│   ├── RfQ.tsx
│   ├── RfQDailyEdit.tsx
│   ├── RfQDetails.tsx
│   ├── RfQList.tsx
│   ├── RfQPriceEdit.tsx
│   ├── Trend.tsx
│   ├── View.tsx
│   ├── Weekly.tsx
│   ├── WeeklyNav.tsx
│   ├── WeeklyProduct.tsx
│   └── WeeklyProductList.tsx
├── index.tsx
├── models
│   ├── App.ts
│   ├── DailyProduct.ts
│   ├── DailyProductList.ts
│   ├── DailyQuantity.ts
│   ├── Info.ts
│   ├── Navigation.ts
│   ├── ProductSelection.ts
│   ├── ProductSelectionItem.ts
│   ├── RFQ.ts
│   ├── RFQList.ts
│   ├── Store.ts
│   ├── Todo.ts
│   ├── WeeklyProduct.ts
│   ├── WeeklyProductList.ts
│   └── i18n.ts
├── react-app-env.d.ts
├── reportWebVitals.ts
├── service-worker.ts
├── serviceWorkerRegistration.ts
├── setupTests.ts
├── static
│   ├── index.scss
│   ├── prognose.scss
│   └── variables.scss
├── tests
│   └── App.test.tsx
└── utils
    ├── date.ts
    └── translate.ts

```


