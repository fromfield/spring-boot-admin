package com.github;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Api：用在类上，说明该类的作用
 * @ApiImplicitParams：用在方法上包含一组参数说明
 * @ApiOperation：用在方法上，说明方法的作用，标注在具体请求上，value和notes的作用差不多，都是对请求进行说明；tags则是对请求进行分类的，比如你有好几个controller，分别属于不同的功能模块，那这里我们就可以使用tags来区分了，看上去很有条理
 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 * paramType：参数放在哪个地方
 * header-->请求参数的获取：@RequestHeader
 * query-->请求参数的获取：@RequestParam
 * path（用于restful接口）-->请求参数的获取：@PathVariable
 * body（不常用）
 * form（不常用）
 * name：参数名
 * dataType：参数类型
 * required：参数是否必须传
 * value：参数的意思
 * defaultValue：参数的默认值
 * @ApiResponses：用于表示一组响应
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 * code：数字，例如400
 * message：信息，例如"请求参数没填好"
 * response：抛出异常的类
 * @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）表明这是一个被swagger框架管理的model，用于class上
 * @ApiModelProperty 这里顾名思义，描述一个model的属性，就是标注在被标注了@ApiModel的class的属性上，这里的value是对字段的描述，example是取值例子，注意这里的example很有用，对于前后端开发工程师理解文档起到了关键的作用，
 * 因为会在api文档页面上显示出这些取值来；这个注解还有一些字段取值，举例说一个：position，表明字段在model中的顺序
 *
 * @author fromfield
 * @create 2017-11-02 10:10
 **/

@Configuration
@EnableSwagger2
public class Swagger2Configure {

    @Bean
    public Docket petApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(generateApiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo generateApiInfo(){
        return new ApiInfoBuilder().title("Spring-boot-Admin Api Doc")
                .contact(new Contact("fromfield", "login", "liyanlongyan@163.com"))
                .description("更多Spring Boot相关文章请关注：https://github.com/fromfield")
                .version("1.0")
                .build();
    }
}