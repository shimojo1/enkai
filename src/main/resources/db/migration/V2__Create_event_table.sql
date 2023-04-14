CREATE TABLE events (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'イベントID',
  name varchar(255) NOT NULL COMMENT 'イベント名',
  detail varchar(255) NOT NULL COMMENT 'イベント詳細',
  max_participant int(11) NOT NULL COMMENT '最大参加者数',
  category_id int(11) NOT NULL COMMENT 'カテゴリID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '作成日時',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='イベント';