export class DocumentStatusKey {
  // todo possibly clear "_tag_docStatus[bla]"
  static Completed = 'docStatusCompleted';
  // noinspection JSUnusedGlobalSymbols
  // static _tag_docStatusCompleted = '.tag-success';

  static Reversed = 'docStatusReversed';
  // noinspection JSUnusedGlobalSymbols
  // static _tag_docStatusReversed = '.tag-default';

  static InProgress = 'docStatusInProgress';
  // noinspection JSUnusedGlobalSymbols
  // static _tag_docStatusInProgress = '.tag-default';

  static Drafted = 'docStatusDrafted';
  // noinspection JSUnusedGlobalSymbols
  // static _tag_docStatusDrafted = '.tag-primary';
}

export class DocumentActionKey {
  static Complete = 'docActionComplete';
  static Void = 'docActionVoid';
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
