export class DocumentStatusKey {
  static Completed = 'docStatusCompleted';
  static Reversed = 'docStatusReversed';
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
}
