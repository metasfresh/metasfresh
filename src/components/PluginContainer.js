import React, { Component } from 'react';
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
  render() {
    const { rawModal, modal, component } = this.props;
    const TagName = component;

    return (
      <Container {...{ modal, rawModal }}>
        <div className="plugin-container" ref={c => (this.container = c)}>
          {TagName && <TagName {...this.props} />}
        </div>
      </Container>
    );
  }
}

function mapStateToProps(state) {
  const { windowHandler } = state;

  const { modal, rawModal } = windowHandler || {
    modal: {},
    rawModal: {},
  };

  return {
    modal,
    rawModal,
  };
}

export default connect(mapStateToProps, {
  redirectPush: push,
})(PluginContainer);

export { pluginWrapper };
