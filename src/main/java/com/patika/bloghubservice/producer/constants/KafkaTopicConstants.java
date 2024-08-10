package com.patika.bloghubservice.producer.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaTopicConstants {
    public static final String BLOG_INDEX_TOPIC = "blog_index_topic";
}
