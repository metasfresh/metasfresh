import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import Container from '../components/Container';

const pluginWrapper = function pluginWrapper(WrappedComponent, ChildComponent) {
  return class WrappedPlugin extends Component {
    render() {
      return <WrappedComponent component={ChildComponent} {...this.props} />;
    }
  };
};

class PluginContainer extends Component {
  renderChildren() {
    const { pluginModal, component, dispatch } = this.props;
    const { store } = this.context;
    const redirectPush = bindActionCreators(push, dispatch);

    const TagName = component;
    const renderedTag = (
      <TagName
        {...this.props}
        redirectPush={redirectPush}
        dispatch={dispatch}
        store={store}
      />
    );

    if (!pluginModal) {
      return <div className="plugin-container">{TagName && renderedTag}</div>;
    }

    return renderedTag;
  }

  render() {
    const { modal, rawModal, pluginModal, breadcrumb } = this.props;

    return (
      <Container {...{ modal, rawModal, pluginModal }} breadcrumb={breadcrumb}>
        {this.renderChildren()}
      </Container>
    );
  }
}

PluginContainer.propTypes = {
  store: PropTypes.object,
  pluginModal: PropTypes.any,
  dispatch: PropTypes.func,
  modal: PropTypes.any,
  rawModal: PropTypes.any,
  breadcrumb: PropTypes.any,
  component: PropTypes.node,
};

function mapStateToProps(state) {
  const { windowHandler, menuHandler } = state;

  const { modal, rawModal, pluginModal } = windowHandler || {
    modal: {},
    rawModal: {},
    pluginModal: {},
  };

  return {
    modal,
    rawModal,
    pluginModal,
    breadcrumb: menuHandler.breadcrumb,
  };
}

export default connect(mapStateToProps)(PluginContainer);
export { pluginWrapper };
