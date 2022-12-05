import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';

import { addNotification } from '../../actions/AppActions';
import * as api from '../../actions/EmailActions';
import SimpleList from '../widget/List/SimpleList';
import Attachments from './Attachments';
import AutocompleteField from './AutocompleteField';

const NewEmail = ({ windowId, docId, handleCloseEmail }) => {
  const [loaded, setLoaded] = useState(false);
  const [dataOnBackend, setDataOnBackend] = useState({});
  const [data, setData] = useState({});
  const [availableTemplates, setAvailableTemplates] = useState([]);
  const [appliedTemplate, setAppliedTemplate] = useState(null);

  const setLoadedEmail = (data) => {
    setDataOnBackend(data);
    setData(data);
    setLoaded(true);
  };

  useEffect(() => {
    api
      .createEmail(windowId, docId)
      .then(setLoadedEmail)
      .catch(() => handleCloseEmail && handleCloseEmail());
  }, [windowId, docId, handleCloseEmail]);

  useEffect(() => {
    api.getAvailableTemplatesArray().then(setAvailableTemplates);
  }, []);

  const onFieldChanged = (property, value, sync) => {
    setData({ ...data, [property]: value });
    if (sync) {
      api.patchEmail(data.emailId, property, value).then(setLoadedEmail);
    }
  };

  const syncToBackend = (property) => {
    const value = data[property];
    if (dataOnBackend[property] === value) return;

    api.patchEmail(data.emailId, property, value).then(setLoadedEmail);
  };

  const dispatch = useDispatch();
  const onSendButtonClicked = () => {
    api.sendEmail(data.emailId).then(() => {
      handleCloseEmail && handleCloseEmail();
      dispatch(
        addNotification('Email', 'Email has been sent.', 5000, 'success')
      );
    });
  };

  const onTemplateChanged = (template) => {
    if (appliedTemplate === template) return;

    api.applyTemplate(data.emailId, template).then((data) => {
      setAppliedTemplate(template);
      setLoadedEmail(data);
    });
  };

  const onFileAttached = (file) => {
    api.addAttachment(data.emailId, file).then(setLoadedEmail);
  };

  if (!loaded) return false;

  return (
    <div className="screen-freeze">
      <div className="panel panel-modal panel-email panel-modal-primary">
        <div className="panel-email-header-wrapper">
          <div className="panel-email-header panel-email-header-top">
            <span className="email-headline">
              {counterpart.translate('window.email.new')}
            </span>
            {availableTemplates.length > 0 && (
              <div className="email-templates">
                <SimpleList
                  list={availableTemplates}
                  onSelect={onTemplateChanged}
                  selected={appliedTemplate}
                />
              </div>
            )}
            <div
              className="input-icon input-icon-lg icon-email"
              onClick={() => handleCloseEmail && handleCloseEmail()}
            >
              <i className="meta-icon-close-1" />
            </div>
          </div>
          <div className="panel-email-header panel-email-bright">
            <div className="panel-email-data-wrapper">
              <span className="email-label">
                {counterpart.translate('window.email.to')}:
              </span>
              <AutocompleteField
                to={data?.to || []}
                onChange={(value) => onFieldChanged('to', value, true)}
                suggestValuesForQueryString={(queryString) =>
                  api.getToTypeahead(data.emailId, queryString)
                }
              />
            </div>
          </div>
          <div className="panel-email-header panel-email-bright">
            <div className="panel-email-data-wrapper">
              <span className="email-label">
                {counterpart.translate('window.email.topic')}:
              </span>
              <input
                className="email-input email-input-msg"
                type="text"
                onChange={(e) => onFieldChanged('subject', e.target.value)}
                value={data.subject ? data.subject : ''}
                onBlur={() => syncToBackend('subject')}
              />
            </div>
          </div>
        </div>
        <div className="panel-email-body">
          <textarea
            value={data.message ? data.message : ''}
            onChange={(e) => onFieldChanged('message', e.target.value)}
            onBlur={() => syncToBackend('message')}
          />
        </div>
        <div className="panel-email-footer">
          <Attachments
            attachments={data.attachments}
            onFileAttached={onFileAttached}
          />
          <button
            onClick={onSendButtonClicked}
            className="btn btn-meta-success btn-sm btn-submit"
          >
            {counterpart.translate('window.email.send')}
          </button>
        </div>
      </div>
    </div>
  );
};

NewEmail.propTypes = {
  windowId: PropTypes.string.isRequired,
  docId: PropTypes.string.isRequired,
  handleCloseEmail: PropTypes.func,
};

export default NewEmail;
