INSERT INTO `Roles`
    (`id`, `name`, `created`, `enabled` )
VALUES
    ( 1, 'ROLE_USER', NOW(), b'1' );
INSERT INTO `Roles`
    (`id`, `name`, `created`, `enabled` )
VALUES
    ( 2, 'ROLE_ADMIN', NOW(), b'1' );

INSERT INTO `Users`
    (`id`, `login`, `password`, email, `created`, `blocked`, `temporary` )
VALUES
    ( 1, 'wilson', 'wilson', 'pv.kazantsev@gmail.com', now(), b'0', b'0' );

INSERT INTO `UserRoles`(`roleId`, `userId` ) VALUES( 1, 1 );
INSERT INTO `UserRoles`(`roleId`, `userId` ) VALUES( 2, 1 );