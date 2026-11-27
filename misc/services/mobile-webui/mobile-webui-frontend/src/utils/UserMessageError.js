/** An error whose text the user shall read */
export class UserMessageError extends Error {
  constructor(messageKey, details, context) {
    super(details ? `${messageKey}: ${details}` : messageKey);
    this.name = 'UserMessageError';
    this.messageKey = messageKey;
    this.details = details;
    this.context = context;
  }
}
