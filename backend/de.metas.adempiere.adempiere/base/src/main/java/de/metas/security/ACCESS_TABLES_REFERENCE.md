# Access Tables Reference

This document provides detailed documentation of all `*_Access` tables in metasfresh that control role-based access to various parts of the system.

---

## Quick Reference

| Table | Controls Access To | Key Columns |
|-------|-------------------|-------------|
| `AD_Window_Access` | WebUI/Swing Windows | `AD_Window_ID`, `IsReadWrite` |
| `AD_Process_Access` | Reports & Processes | `AD_Process_ID`, `IsReadWrite` |
| `AD_Form_Access` | Special Forms | `AD_Form_ID`, `IsReadWrite` |
| `AD_Workflow_Access` | Workflows | `AD_Workflow_ID`, `IsReadWrite` |
| `AD_Task_Access` | OS Tasks | `AD_Task_ID`, `IsReadWrite` |
| `AD_Table_Access` | Database Tables | `AD_Table_ID`, `IsExclude`, `IsReadOnly`, `IsCanReport`, `IsCanExport` |
| `AD_Column_Access` | Table Columns | `AD_Column_ID`, `IsExclude`, `IsReadOnly` |
| `AD_Document_Action_Access` | Document Actions (Complete, Void, etc.) | `C_DocType_ID`, `AD_Ref_List_ID` |
| `AD_Role_OrgAccess` | Organizations (per role) | `AD_Org_ID`, `IsReadOnly` |
| `AD_User_OrgAccess` | Organizations (per user) | `AD_User_ID`, `AD_Org_ID`, `IsReadOnly` |
| `AD_Role_TableOrg_Access` | Table-specific Org Access | `AD_Table_ID`, `AD_Org_ID`, `Access` |
| `AD_Private_Access` | Private Record Access | `AD_Table_ID`, `Record_ID`, `AD_User_ID`/`AD_UserGroup_ID` |
| `AD_User_Record_Access` | Fine-grained Record Access | `AD_Table_ID`, `Record_ID`, `AD_User_ID`/`AD_UserGroup_ID`, `Access` |
| `AD_Role_Record_Access_Config` | Record Access Configuration | `AD_Table_ID`, `Type` |
| `AD_UserBPAccess` | Business Partner Portal Access | `AD_User_ID`, `BPAccessType`, `DocBaseType` |
| `Mobile_Application_Access` | Mobile Apps | `Mobile_Application_ID`, `IsAllowAllActions` |
| `Mobile_Application_Action_Access` | Mobile App Actions | `Mobile_Application_ID`, `Mobile_Application_Action_ID` |

---

## UI Element Access Tables

These tables control access to user interface elements.

### AD_Window_Access

**Purpose:** Controls which roles can access which windows (UI screens).

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role being granted access |
| `AD_Window_ID` | FK | The window (AD_Window) |
| `IsReadWrite` | Y/N | `Y` = full access, `N` = read-only |

**metasfresh Usage:**
- Windows are the main UI entry points (e.g., "Business Partner", "Sales Order")
- Read-write enables creating/editing records; read-only allows viewing only
- Auto-maintained for non-manual roles via `role_access_update_windows()`

**Java API:**
```java
Boolean access = permissions.getWindowAccess(windowId);
// null = no access, false = readonly, true = readwrite
```

---

### AD_Process_Access

**Purpose:** Controls which roles can execute which processes (reports, actions).

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role being granted access |
| `AD_Process_ID` | FK | The process (AD_Process) |
| `IsReadWrite` | Y/N | `Y` = can execute, `N` = can view only |

**metasfresh Usage:**
- Processes include reports, data exports, and action buttons
- Examples: "Print Invoice", "Generate Shipments", "Mass Update"
- Auto-maintained via `role_access_update_processes()`

**Java API:**
```java
Boolean access = permissions.getProcessAccess(processId);
```

---

### AD_Form_Access

**Purpose:** Controls which roles can access special forms (custom UI screens).

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role being granted access |
| `AD_Form_ID` | FK | The form (AD_Form) |
| `IsReadWrite` | Y/N | Access level |

**metasfresh Usage:**
- Forms are specialized screens not based on standard window layout
- Examples: "SQL Process", "Tree Maintenance", "Archive Viewer", "MRP Info"
- Auto-maintained via `role_access_update_forms()`

**Java API:**
```java
Boolean access = permissions.getFormAccess(formId);
```

---

### AD_Workflow_Access

**Purpose:** Controls which roles can access which workflows.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role being granted access |
| `AD_Workflow_ID` | FK | The workflow (AD_Workflow) |
| `IsReadWrite` | Y/N | Access level |

**metasfresh Usage:**
- Workflows define document processing and approval chains
- Used for document completion, approval routing
- Auto-maintained via `role_access_update_workflows()`

**Java API:**
```java
Boolean access = permissions.getWorkflowAccess(workflowId);
```

---

### AD_Task_Access

**Purpose:** Controls which roles can execute OS-level tasks.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role being granted access |
| `AD_Task_ID` | FK | The task (AD_Task) |
| `IsReadWrite` | Y/N | Access level |

**metasfresh Usage:**
- Tasks are OS-level commands (rarely used in modern metasfresh)
- Examples: "Java Version", "Database Export", "Database Transfer"
- Auto-maintained via `role_access_update_tasks()`

**Java API:**
```java
Boolean access = permissions.getTaskAccess(taskId);
```

---

## Data Access Tables

These tables control access to database tables, columns, and records.

### AD_Table_Access

**Purpose:** Controls table-level data access rules (include/exclude patterns).

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `AD_Table_ID` | FK | The table (AD_Table) |
| `AccessTypeRule` | char | `I` = Include, `E` = Exclude |
| `IsReadOnly` | Y/N | `Y` = read-only access |
| `IsCanReport` | Y/N | `Y` = can generate reports from this table |
| `IsCanExport` | Y/N | `Y` = can export data from this table |
| `IsExclude` | Y/N | `Y` = exclude this table from access |

**metasfresh Usage:**
- Fine-tune table access beyond window-level permissions
- Use `IsExclude=Y` to blacklist specific tables
- Control reporting and export capabilities per table

**Java API:**
```java
boolean canAccess = permissions.isTableAccess(tableId, Access.READ);
boolean canReport = permissions.isCanReport(tableId);
boolean canExport = permissions.isCanExport(tableId);
```

---

### AD_Column_Access

**Purpose:** Controls column-level access restrictions.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `AD_Table_ID` | FK | The table (optional, for context) |
| `AD_Column_ID` | FK | The column (AD_Column) |
| `IsExclude` | Y/N | `Y` = hide this column |
| `IsReadOnly` | Y/N | `Y` = column is read-only |

**metasfresh Usage:**
- Hide sensitive columns (e.g., cost prices, internal notes)
- Make specific columns read-only for certain roles
- Applied on top of window/table permissions

**Java API:**
```java
boolean canAccess = permissions.isColumnAccess(tableId, columnId, Access.READ);
```

---

### AD_Document_Action_Access

**Purpose:** Controls which document actions a role can perform per document type.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `C_DocType_ID` | FK | The document type |
| `AD_Ref_List_ID` | FK | The document action (from AD_Reference 135) |

**Document Actions (AD_Reference_ID=135):**

| Value | Name (German) | English |
|-------|---------------|---------|
| `CO` | Fertigstellen | Complete |
| `AP` | Genehmigen | Approve |
| `RJ` | Ablehnen | Reject |
| `PR` | Vorbereiten | Prepare |
| `IN` | Annulieren | Invalidate |
| `VO` | Stornieren | Void |
| `CL` | Schliessen | Close |
| `RE` | Reaktivieren | Reactivate |
| `RC` | Storno | Reverse Correct |
| `RA` | Rückbuchung | Reverse Accrual |
| `PO` | Buchen | Post |
| `XL` | Entsperren | Unlock |
| `UC` | Öffnen | Unlock |
| `WC` | Warten und fertigstellen | Wait Complete |
| `--` | Verarbeitung (Nichts) | None |

**metasfresh Usage:**
- Restrict who can complete, void, or reverse documents
- Different permissions per document type (Sales Order vs Purchase Order)
- Auto-maintained via `role_access_update_docactions()`

---

## Organization Access Tables

These tables control which organizations a user/role can access.

### AD_Role_OrgAccess

**Purpose:** Defines which organizations a role can access.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `AD_Org_ID` | FK | The organization |
| `IsReadOnly` | Y/N | `Y` = read-only access to this org |

**metasfresh Usage:**
- Primary mechanism for org-level security
- Multiple orgs can be assigned to one role
- Use `AD_Role.IsAccessAllOrgs=Y` to bypass this table

**Java API:**
```java
boolean canAccess = permissions.isOrgAccess(orgId, tableName, Access.WRITE);
Set<OrgId> orgs = permissions.getAD_Org_IDs_AsSet();
```

---

### AD_User_OrgAccess

**Purpose:** Defines organization access at user level (overrides role).

| Column | Type | Description |
|--------|------|-------------|
| `AD_User_ID` | FK | The user |
| `AD_Org_ID` | FK | The organization |
| `IsReadOnly` | Y/N | `Y` = read-only access |

**metasfresh Usage:**
- Only used when `AD_Role.IsUseUserOrgAccess=Y`
- Allows user-specific org restrictions different from their role
- Useful for shared roles with per-user org limitations

---

### AD_Role_TableOrg_Access

**Purpose:** Table-specific organization access rules.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `AD_Table_ID` | FK | The table |
| `AD_Org_ID` | FK | The organization |
| `Access` | varchar | Access level |

**metasfresh Usage:**
- Restrict org access for specific tables only
- Example: A role can see all orgs, but only their own org's invoices
- More granular than `AD_Role_OrgAccess`

---

## Record-Level Access Tables

These tables control access to individual records.

### AD_Private_Access

**Purpose:** Marks records as private to specific users or user groups.

| Column | Type | Description |
|--------|------|-------------|
| `AD_User_ID` | FK | The user (nullable) |
| `AD_UserGroup_ID` | FK | The user group (nullable) |
| `AD_Table_ID` | FK | The table containing the record |
| `Record_ID` | numeric | The specific record ID |

**metasfresh Usage:**
- Either `AD_User_ID` or `AD_UserGroup_ID` must be set
- When a record is "locked", only the owner can access it
- Used with `AD_Role.IsPersonalLock=Y` capability

**Java Processes:**
- `RecordPrivateAccess_Add` - Grant private access
- `RecordPrivateAccess_Remove` - Revoke private access

---

### AD_User_Record_Access

**Purpose:** Fine-grained record access with time validity and audit trail.

| Column | Type | Description |
|--------|------|-------------|
| `AD_User_ID` | FK | The user (nullable) |
| `AD_UserGroup_ID` | FK | The user group (nullable) |
| `AD_Table_ID` | FK | The table |
| `Record_ID` | numeric | The record ID |
| `Access` | char | Access level (R=Read, W=Write) |
| `ValidFrom` | timestamp | Start of access validity |
| `ValidTo` | timestamp | End of access validity |
| `PermissionIssuer` | varchar | Who granted the access |
| `Parent_ID` | FK | Parent access record (for cascading) |
| `Root_ID` | FK | Root access record |
| `Description` | text | Reason/notes |

**metasfresh Usage:**
- Most flexible record access mechanism
- Supports time-limited access
- Tracks permission source via `PermissionIssuer`
- Supports cascading permissions (parent/root)

**Java Service:**
```java
@Autowired RecordAccessService recordAccessService;

recordAccessService.grantAccess(RecordAccessGrantRequest.builder()
    .recordRef(TableRecordReference.of("C_BPartner", bpartnerId))
    .principal(Principal.userId(userId))
    .permission(Access.READ)
    .issuer(PermissionIssuer.MANUAL)
    .validFrom(LocalDate.now())
    .validTo(LocalDate.now().plusMonths(3))
    .build());
```

---

### AD_Role_Record_Access_Config

**Purpose:** Configuration for record-level access per role and table.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `AD_Table_ID` | FK | The table (nullable = all tables) |
| `Type` | varchar | Configuration type |

**metasfresh Usage:**
- Enables/configures record access for specific tables
- Controls how `AD_User_Record_Access` is applied
- Managed via `RecordAccessConfigService`

---

## Business Partner Portal Access

### AD_UserBPAccess

**Purpose:** Controls what business partner contacts can access in the portal.

| Column | Type | Description |
|--------|------|-------------|
| `AD_User_ID` | FK | The BP contact user |
| `BPAccessType` | char | Type of access granted |
| `R_RequestType_ID` | FK | Request type (for `R` access) |
| `DocBaseType` | char | Document base type (for `B` access) |

**BPAccessType Values:**

| Value | Name | Description |
|-------|------|-------------|
| `A` | Assets, Download | Access to downloadable assets |
| `B` | Business Documents | Access to business documents (orders, invoices) |
| `R` | Requests | Access to service requests |

**metasfresh Usage:**
- Controls BP portal/webshop access
- `DocBaseType` filters which documents are visible (e.g., only sales orders)
- `R_RequestType_ID` limits request types visible

---

## Mobile Application Access

### Mobile_Application_Access

**Purpose:** Controls which roles can access which mobile applications.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `Mobile_Application_ID` | FK | The mobile app |
| `IsAllowAllActions` | Y/N | `Y` = grant all actions, `N` = use action-level access |

**Mobile Applications:**
- `Kommissionierung` - Picking
- `Produktion` - Manufacturing
- `Bereitstellung` - Distribution
- `HU Manager` - Handling Unit Management
- `POS` - Point of Sale
- `Inventur` - Inventory

**metasfresh Usage:**
- Controls access to mobile-webui applications
- If `IsAllowAllActions=N`, use `Mobile_Application_Action_Access` for granular control
- Auto-maintained via `role_access_update_mobileapplications()`

---

### Mobile_Application_Action_Access

**Purpose:** Controls which specific actions within a mobile app a role can perform.

| Column | Type | Description |
|--------|------|-------------|
| `AD_Role_ID` | FK | The role |
| `Mobile_Application_ID` | FK | The mobile app |
| `Mobile_Application_Action_ID` | FK | The specific action |

**Example Actions (HU Manager):**
- `dispose` - Dispose handling units
- `move` - Move HUs between locators
- `setClearanceStatus` - Set clearance status
- `setCurrentLocator` - Set current locator
- `bulkActions` - Perform bulk operations
- `changeQty` - Change quantities
- `printLabels` - Print labels

**metasfresh Usage:**
- Fine-grained control when `Mobile_Application_Access.IsAllowAllActions=N`
- Different roles can have different action sets within the same app

---

## Auto-Maintenance Functions

For roles with `IsManual='N'`, access records are automatically maintained:

```sql
-- Update all access for a role
SELECT role_access_update(p_ad_role_id := <role_id>, p_createdby := <user_id>);

-- Individual functions:
role_access_update_windows(...)
role_access_update_processes(...)
role_access_update_forms(...)
role_access_update_workflows(...)
role_access_update_tasks(...)
role_access_update_docactions(...)
role_access_update_mobileapplications(...)
```

These functions examine:
- The role's `UserLevel` (System/Client/Org scope)
- The element's `AccessLevel` in the Application Dictionary
- Grant access where levels match

---

## See Also

- [AD_Role Window](https://docs.metasfresh.org/webui_collection/EN/NewUserRole.html) - WebUI documentation
