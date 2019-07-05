export class DocumentStatusKey {
  static Completed = 'docStatusCompleted';
  // noinspection JSUnusedGlobalSymbols
  static _tag_docStatusCompleted = '.tag-success';


  static InProgress = 'docStatusInProgress';
  // noinspection JSUnusedGlobalSymbols
  static _tag_docStatusInProgress = '.tag-default';

  static Drafted = 'docStatusDrafted';
  // noinspection JSUnusedGlobalSymbols
  static _tag_docStatusDrafted = '.tag-primary';
}

export class DocumentActionKey {
  static Complete = 'docActionComplete';
}

/**
 * These constants should be used instead of writing strings every time
 */
export class RewriteURL {
  // noinspection JSUnusedGlobalSymbols
  /**
   * WINDOW is the default
   */
  static WINDOW = '/rest/api/window/.*[^/][^N][^E][^W]$';

  static PROCESS = '/rest/api/process/';
}
