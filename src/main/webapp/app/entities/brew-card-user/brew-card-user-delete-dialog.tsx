// import React, { useEffect, useState } from 'react';
// import { useLocation, useNavigate, useParams } from 'react-router-dom';
// import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
//
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
//
// import { useAppDispatch, useAppSelector } from 'app/config/store';
// import { getEntityUser, deleteEntityUser } from './brew-card-user.reducer';
//
// export const BrewCardUserDeleteDialog = () => {
//   const dispatch = useAppDispatch();
//
//   const location = useLocation();
//   const navigate = useNavigate();
//   const { id } = useParams<'id'>();
//
//   const [loadModal, setLoadModal] = useState(false);
//
//   useEffect(() => {
//     dispatch(getEntityUser(id));
//     setLoadModal(true);
//   }, []);
//
//   const brewCardUserEntity = useAppSelector(state => state.brewCardUser.entity);
//   const updateSuccess = useAppSelector(state => state.brewCardUser.updateSuccess);
//
//   const handleClose = () => {
//     navigate('/brew-card/user');
//   };
//
//   useEffect(() => {
//     if (updateSuccess && loadModal) {
//       handleClose();
//       setLoadModal(false);
//     }
//   }, [updateSuccess]);
//
//   const confirmDelete = () => {
//     dispatch(deleteEntityUser(brewCardUserEntity.id));
//   };
//
//   return (
//     <Modal isOpen toggle={handleClose}>
//       <ModalHeader toggle={handleClose} data-cy="brewCardDeleteDialogHeading">
//         Confirm delete operation
//       </ModalHeader>
//       <ModalBody id="passionprojectApp.brewCard.delete.question">Are you sure you want to delete Brew Card {brewCardUserEntity.id}?</ModalBody>
//       <ModalFooter>
//         <Button color="secondary" onClick={handleClose}>
//           <FontAwesomeIcon icon="ban" />
//           &nbsp; Cancel
//         </Button>
//         <Button id="jhi-confirm-delete-brewCard" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
//           <FontAwesomeIcon icon="trash" />
//           &nbsp; Delete
//         </Button>
//       </ModalFooter>
//     </Modal>
//   );
// };
//
// export default BrewCardUserDeleteDialog;
