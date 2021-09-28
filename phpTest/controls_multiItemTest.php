<?php 

$data =  array('type' => 'multi_item',
				'value' => '',
				'record_name' => 'description',
				'start_check_type'=> 'ipv6',
				'end_check_type' => 'ipv6');

$controlItem = new SectionControlMultiItemDouble('test','controlTest',$data);

?>

<form  class="form-horizontal" >
	<div>
		<div class="box span12"> 
			<div class="box-header well" data-original-title>
				<h2 id="title"><i class="icon-globe"></i> MultiItems Test control </h2>
			</div>
			<div class="box-content">	

				<div class="control-group">
					<label class="control-label"><?php echo Dictionary::tryValue($controlItem->getTitleCode()); ?></label>
					<div class="controls">
						<?php echo $controlItem->getHTML(); ?>
					</div>
				</div>		

			</div>
			<button id="get-items-action">Get result</button>
			<pre id="result-item-action"></pre>
		</div>
	</div>
</form>
