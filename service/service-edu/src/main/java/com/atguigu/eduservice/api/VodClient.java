package com.atguigu.eduservice.api;

import com.atguigu.commonutlis.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    // 阿里云批量删除视频
    @DeleteMapping("/vodservice/vod/deleteBatch")
    R deleteBatchVideoByIds(List<String> ids);

    // 阿里云删除视频
    @DeleteMapping("/vodservice/vod/delete/{id}")
    R deleteVideoById(@PathVariable(value = "id") String id);
}
