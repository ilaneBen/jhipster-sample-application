import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVendeur } from 'app/shared/model/vendeur.model';
import { getEntities as getVendeurs } from 'app/entities/vendeur/vendeur.reducer';
import { IPrime } from 'app/shared/model/prime.model';
import { getEntity, updateEntity, createEntity, reset } from './prime.reducer';

export const PrimeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vendeurs = useAppSelector(state => state.vendeur.entities);
  const primeEntity = useAppSelector(state => state.prime.entity);
  const loading = useAppSelector(state => state.prime.loading);
  const updating = useAppSelector(state => state.prime.updating);
  const updateSuccess = useAppSelector(state => state.prime.updateSuccess);

  const handleClose = () => {
    navigate('/prime');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVendeurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.montant !== undefined && typeof values.montant !== 'number') {
      values.montant = Number(values.montant);
    }

    const entity = {
      ...primeEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...primeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.prime.home.createOrEditLabel" data-cy="PrimeCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.prime.home.createOrEditLabel">Create or edit a Prime</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="prime-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.prime.nomVendeur')}
                id="prime-nomVendeur"
                name="nomVendeur"
                data-cy="nomVendeur"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.prime.montant')}
                id="prime-montant"
                name="montant"
                data-cy="montant"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prime" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PrimeUpdate;
