# Main API flow

This document describes general ideas behind API communication, step-by-step.

API calls described here will base on example of "Sales order" document no 1023 (ID=7765).

Entity type for "Sales order" is `143`.

## Flow step-by-step

### 1) Get page layout and widget informations

First we need to fetch basic information of how to layout page that is going to be displayed to the user.

`GET /api/layout?type=143`

> **type** - window type identifier

[layout.json – example response](1-response-layout.json)

Structure of response describes:

* `sections` - describes a main form sections.
* `tabs` – describes bottom tabbed content. It's an array of possible tabs.

### 2) Load entity data into page

#### Get initial field states and data for **new** entity:

`GET /api/data?type=143&id=NEW`

> **type** - window type identifier
> **id** - `NEW` string flag

[data.json – example response](2-response-data.json)

> **Heads up!** First API call with `NEW` as a `id` value will generate **temporary ID** to use in future calls. This ID is a subject to change in process of committing changes.

#### Get initial field states and data for **existing** entity:

`GET /api/data?type=143&id=7765`

> **type** - window type identifier
> **id** - entity identifier to load

[data.json – example response](2-response-data.json)

### 3) Commit some changed data

User has made some changes so it's time to save them on backend.

`PATCH /api/commit?type=143&id=7765`

> **type** - window type identifier
> **id** - entity identifier to modify

[commit.json – example request body](3-request-commit.json)

[commit.json – example response](3-response-commit.json)

### 4) Load tab data

`GET /api/data?type=143&id=7765&tabid=1`

> **type** - window type identifier
> **id** - entity identifier
> **tabid** - tab identifier to load data for

[data.json – example response](4-response-tab-data.json)

### 5) Commit changed tab data

`PATCH /api/commit?type=143&id=7765&tabid=1&rowid=14`

> **type** - window type identifier
> **id** - entity identifier
> **tabid** - tab identifier
> **rowid** - identifier of row to edit

Request and response body have exactly the same construction as in Step 3.

## Batch operations (WIP)

TBA: Batch operations, f.e. for multiple rows edition/deletion inside of tabs.

## Pagination (WIP)

TBA