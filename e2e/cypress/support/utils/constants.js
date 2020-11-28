export class DocumentStatusKey {
  static Completed = 'docStatusCompleted';
  static Reversed = 'docStatusReversed';
  // noinspection JSUnusedGlobalSymbols
  static Voided = 'docStatusVoided';
  static InProgress = 'docStatusInProgress';
  static Drafted = 'docStatusDrafted';
}

export class DocumentActionKey {
  static Complete = 'docActionComplete';
  static Void = 'docActionVoid';
  static Reactivate = 'docActionReactivate';
  static Reverse = 'docActionReverse';
}

/**
 * These constants should be used instead of writing strings every time
 */
export class RewriteURL {
  // noinspection JSUnusedGlobalSymbols
  /**
   * WINDOW is the default
   */
  static WINDOW = '/rest/api/window/';

  static PROCESS = '/rest/api/process/';

  static ATTRIBUTE = '/rest/api/pattribute';

  static QUICKACTION = `/rest/api/process/.*/start$`;

  static QuickActions = `rest/api/documentView/.*/quickActions?`;

  static Filters = `rest/api/documentView/.*/filter`;

  static REFERENCES = `/rest/api/window/.*/references/sse$`;
  // ^^ It seems there is an issue with SSE network requests support in cypress. On current version 4.6.0 they are not tracked

  static DocActionDropdown = `/rest/api/window/[0-9]+/[0-9]+/field/DocAction/dropdown`;

  static DocumentLayout = `/rest/api/.*/layout`;

  // noinspection JSUnusedGlobalSymbols
  static SingleView = new RegExp(`/window/\\d+/\\d+$`);

  // noinspection JSUnusedGlobalSymbols
  static ExactSingleView(windowId) {
    return new RegExp(`/window/${windowId}/\\d+$`);
  }

  static Generic = `/rest/api/\\D+/`;
}
