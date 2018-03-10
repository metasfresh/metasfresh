import counterpart from 'counterpart';
import { Component } from 'react';
import deepForceUpdate from 'react-deep-force-update';

import { getMessages } from '../actions/AppActions';

class Translation extends Component {
  constructor(props) {
    super(props);
  }

  componentWillMount = () => {
    getMessages().then(response => {
      counterpart.registerTranslations('lang', response.data);
      counterpart.setLocale('lang');
      counterpart.setMissingEntryGenerator(function(key) {
        // eslint-disable-next-line no-console
        console.error(`Missing translation: ${key}`);
        return `{${key}}`;
      });

      deepForceUpdate(this);
    });
  };

  render = () => this.props.children;
}

export default Translation;
