/**
 * Created by 01 on 2017/5/3.
 */
var uploader=null;
var page={
    init:function () {
        this.upload();
        // this.selCover();   //选择上传封面
    },
    upload:function () {
        if(uploader==null){
            uploader = new plupload.Uploader({ //实例化一个plupload上传对象
                browse_button : 'upload',
                url : 'http://127.0.0.1:8080/file/upload/',
                multipart:true,
                filters: {
                    mime_types : [ //只允许上传图片文件
                        { title : "图片文件", extensions : "jpg,gif,png" }
                    ]
                }
            });
        }
        uploader.init(); //初始化

        //绑定文件添加进队列事件
        uploader.bind('FilesAdded',function(uploader,files){
            debugger;
            for(var i = 0, len = files.length; i<len; i++){
                !function(i){
                    previewImage(files[i],function(imgsrc){
                        var html='<img src="'+ imgsrc +'" />';
                        $('.photo-bg').html(html);
                    })
                }(i);
            }
            uploader.start();
        });

        //plupload中为我们提供了mOxie对象
        //有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
        //如果你不想了解那么多的话，那就照抄本示例的代码来得到预览的图片吧
        function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
            if(!file || !/image\//.test(file.type)) return; //确保文件是图片
            if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
                var fr = new mOxie.FileReader();
                fr.onload = function(){
                    callback(fr.result);
                    fr.destroy();
                    fr = null;
                }
                fr.readAsDataURL(file.getSource());
            }else{
                var preloader = new mOxie.Image();
                preloader.onload = function() {
                    preloader.downsize( 300, 300 );//先压缩一下要预览的图片,宽300，高300
                    var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
                    callback && callback(imgsrc); //callback传入的参数为预览图片的url
                    preloader.destroy();
                    preloader = null;
                };
                preloader.load( file.getSource() );
            }
        }

        uploader.bind("FileUploaded",function(uploader,file,responseObject){
            $("#fileSrc").val(responseObject.response);
        });
    }
    // selCover:function () {
    //     $('.covers>li').on('click',function () {
    //         var html=$(this).html();
    //         $('.photo-bg').html(html);
    //         $("#fileSrc").val($(this).find("img").attr("src"));
    //     })
    // }
};
$(function () {
    page.init();
});
