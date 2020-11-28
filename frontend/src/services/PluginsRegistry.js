export default class PluginsRegistry {
  constructor(hostApplication) {
    this.hostApp = hostApplication;
    this.plugins = {};
  }

  getEntry(pluginName) {
    if (pluginName) {
      return this.plugins[`${pluginName}`];
    }
  }

  addEntry(name, file) {
    this.plugins[`${name}`] = file;
  }
}
