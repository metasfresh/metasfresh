import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { activateTab, unselectTab } from '../../actions/WindowActions';
import Tab from './Tab';

class Tabs extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: props.children[0].key,
    };
  }

  componentDidMount = () => {
    this.props.dispatch(activateTab('master', this.state.selected));
  };

  componentDidUpdate = (prevProps, prevState) => {
    const { dispatch } = this.props;
    if (prevState.selected !== this.state.selected) {
      dispatch(activateTab('master', this.state.selected));
    }
  };

  componentWillUnmount() {
    this.props.dispatch(unselectTab('master'));
  }

  handleClick = (e, id) => {
    e.preventDefault();
    this.setState({
      selected: id,
    });
  };

  handlePillKeyDown = (e, key) => {
    if (e.key === 'Enter') {
      this.handleClick(e, key);
    }
  };

  renderNestedPills = (parentItem, maxWidth, level, nestedPills) => {
    // const { selected } = this.state;
    // const pillsArray = [];
    // nestedPills[level] = [];

    const pillsArray = parentItem.map(item => {
      // nestedPills[level].push(this.renderPill(item, maxWidth));

      if (parentItem.tabs) {
        this.renderNestedPills(item, maxWidth, level++, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    // return (
    //   <ul className="nav nav-tabs nested-tabs">
    //   </ul>
    // );
    nestedPills[level] = [
      <ul className="nav nav-tabs nested-tabs">{pillsArray}</ul>     
    ];
  };

  renderPill = (item, maxWidth) => {
    const { tabIndex, modalVisible } = this.props;
    const { selected } = this.state;

    return (
      <li
        id={`tab_${item.internalName}`}
        className="nav-item"
        key={'tab' + item.tabId}
        onClick={e => this.handleClick(e, item.tabId)}
        tabIndex={modalVisible ? -1 : tabIndex}
        onKeyDown={e => this.handlePillKeyDown(e, item.tabId)}
        style={{ maxWidth }}
        title={item.description || item.caption}
      >
        <a
          className={classnames('nav-link', {
            active: selected === item.tabId,
          })}
        >
          {item.caption}
        </a>
      </li>
    );
  };

  renderPills = pills => {
    const maxWidth = 95 / pills.length + '%';
    const { selected } = this.state;
    // const nestedPills = {};
    const nestedPills = [];

    const pillsArray = pills.map(item => {
      if (item.tabs && selected === item.key) {
        this.renderNestedPills(item, maxWidth, 0, nestedPills);
      }

      return this.renderPill(item, maxWidth);
    });

    console.log('NESTEDPILLS: ', nestedPills);

    return (
      <div className="tabs-wrap">
        <ul className="nav nav-tabs mt-1">{pillsArray}</ul>
        {/*{this.renderNestedPills(pills)}*/}
        {nestedPills}
      </div>
    );
  };

  renderTabs = tabs => {
    const { toggleTableFullScreen, fullScreen, windowType } = this.props;
    const { selected } = this.state;

    return tabs.map(item => {
      const itemWithProps = Object.assign({}, item, {
        props: Object.assign({}, item.props, {
          toggleFullScreen: toggleTableFullScreen,
          fullScreen: fullScreen,
        }),
      });

      if (selected == item.key) {
        const { tabId, queryOnActivate, docId, orderBy } = item.props;

        return (
          <div key={'pane' + item.key} className="tab-pane active">
            <Tab
              {...{
                queryOnActivate,
                tabId,
                docId,
                windowType,
                orderBy,
              }}
            >
              {itemWithProps}
            </Tab>
          </div>
        );
      } else {
        return false;
      }
    });
  };

  render() {
    const { children, fullScreen, tabs } = this.props;

    return (
      <div
        className={classnames('mb-1', {
          'tabs-fullscreen container-fluid': fullScreen,
        })}
      >
        {this.renderPills(tabs)}
        <div className="tab-content" ref={c => (this.tabContent = c)}>
          {this.renderTabs(children)}
        </div>
      </div>
    );
  }
}

Tabs.propTypes = {
  dispatch: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool.isRequired,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(Tabs);
