package com.crux.view.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.crux.util.StringUtils;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class CruxImageView extends GenericDraweeView {

    public interface Callbacks {
        void onImageDownloaded(Bitmap bitmap);
    }

    private Callbacks mCallbacks;

    public CruxImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public CruxImageView(Context context) {
        super(context);
    }

    public CruxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CruxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageURI(int resId, String remoteUrl) {
        if (resId != 0) {
            setImageURI(resId);
        } else {
            setImageURI(remoteUrl);
        }
    }

    public void setImageURI(int resId) {
        setImage(getAppResource(resId));
    }

    public void setImageURI(String remoteUrl) {
        if (StringUtils.isEmpty(remoteUrl)) {
            return;
        }
        setImage(getNetworkUri(remoteUrl));
    }

    public void setImage(Uri uri) {
        if (uri == null) {
            return;
        }

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, null);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                 @Override
                                 public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                     if (mCallbacks != null) {
                                         mCallbacks.onImageDownloaded(bitmap);
                                     }
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                 }
                             },
                CallerThreadExecutor.getInstance());

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(this.getController())
                        .build();

        this.setController(controller);
    }

    public Callbacks getCallbacks() {
        return mCallbacks;
    }

    public void setCallbacks(Callbacks mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    private Uri getAppResource(int resId) {
        return (new Uri.Builder()).scheme("res").path(String.valueOf(resId)).build();
    }

    private Uri getNetworkUri(String remoteUrl) {
        try {
            return Uri.parse(remoteUrl);
        } catch (Exception ex) {
            return null;
        }
    }

}
