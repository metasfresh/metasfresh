export class ContextualError extends Error {
  constructor(message, context = {}) {
    super(message);
    this.name = 'ContextualError';
    this.userFriendlyError = true;
    this.context = context;
  }
}
