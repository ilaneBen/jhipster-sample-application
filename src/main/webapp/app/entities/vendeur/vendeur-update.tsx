import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrime } from 'app/shared/model/prime.model';
import { getEntities as getPrimes } from 'app/entities/prime/prime.reducer';
import { IProduits } from 'app/shared/model/produits.model';
import { getEntities as getProduits } from 'app/entities/produits/produits.reducer';
import { IVendeur } from 'app/shared/model/vendeur.model';
import { getEntity, updateEntity, createEntity, reset } from './vendeur.reducer';

export const VendeurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const primes = useAppSelector(state => state.prime.entities);
  const produits = useAppSelector(state => state.produits.entities);
  const vendeurEntity = useAppSelector(state => state.vendeur.entity);
  const loading = useAppSelector(state => state.vendeur.loading);
  const updating = useAppSelector(state => state.vendeur.updating);
  const updateSuccess = useAppSelector(state => state.vendeur.updateSuccess);

  const handleClose = () => {
    navigate('/vendeur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPrimes({}));
    dispatch(getProduits({}));
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
    if (values.nbrVendu !== undefined && typeof values.nbrVendu !== 'number') {
      values.nbrVendu = Number(values.nbrVendu);
    }

    const entity = {
      ...vendeurEntity,
      ...values,
      primes: mapIdList(values.primes),
      produits: produits.find(it => it.id.toString() === values.produits.toString()),
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
          ...vendeurEntity,
          primes: vendeurEntity?.primes?.map(e => e.id.toString()),
          produits: vendeurEntity?.produits?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleApplicationApp.vendeur.home.createOrEditLabel" data-cy="VendeurCreateUpdateHeading">
            <Translate contentKey="jhipsterSampleApplicationApp.vendeur.home.createOrEditLabel">Create or edit a Vendeur</Translate>
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
                  id="vendeur-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.vendeur.nom')}
                id="vendeur-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.vendeur.nbrVendu')}
                id="vendeur-nbrVendu"
                name="nbrVendu"
                data-cy="nbrVendu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.vendeur.objectifAtteint')}
                id="vendeur-objectifAtteint"
                name="objectifAtteint"
                data-cy="objectifAtteint"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('jhipsterSampleApplicationApp.vendeur.prime')}
                id="vendeur-prime"
                data-cy="prime"
                type="select"
                multiple
                name="primes"
              >
                <option value="" key="0" />
                {primes
                  ? primes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vendeur-produits"
                name="produits"
                data-cy="produits"
                label={translate('jhipsterSampleApplicationApp.vendeur.produits')}
                type="select"
              >
                <option value="" key="0" />
                {produits
                  ? produits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vendeur" replace color="info">
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

export default VendeurUpdate;
