CREATE TABLE chats (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'チャットID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  event_id int(11) NOT NULL COMMENT 'イベントID',
  body varchar(255) NOT NULL COMMENT '本文',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '作成日時',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='チャット';