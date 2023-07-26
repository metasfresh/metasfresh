import { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import deepForceUpdate from 'react-deep-force-update';
import { connect } from 'react-redux';

import { getMessages } from '../actions/AppActions';

// Fake singleton
let INSTANCE = null;

class Translation extends Component {
  static getMessages = () => {
    return getMessages().then((response) => {
      if (window.Cypress) {
        window.Cypress.emit('emit:counterpartTranslations', response.data);
      }

      counterpart.registerTranslations('lang', response.data);
      counterpart.setLocale('lang');
      counterpart.setMissingEntryGenerator(function (key) {
        // eslint-disable-next-line no-console
        console.error(`Missing translation: ${key}`);
        return `{${key}}`;
      });

      deepForceUpdate(INSTANCE);
    });
  };

  constructor(props) {
    super(props);

    INSTANCE = this;
  }

  UNSAFE_componentWillMount = () => {
    Translation.getMessages();
  };

  UNSAFE_componentWillReceiveProps(nextProps) {
    if (nextProps.language !== this.props.language) {
      Translation.getMessages();
    }
  }

  render = () => this.props.children;
}

Translation.propTypes = {
  children: PropTypes.any,
  language: PropTypes.string,
};

const mapStateToProps = ({ appHandler: { me } }) => {
  const language = me.language ? me.language.key : 'de_DE';

  return {
    language: language,
  };
};

export default connect(mapStateToProps)(Translation);
